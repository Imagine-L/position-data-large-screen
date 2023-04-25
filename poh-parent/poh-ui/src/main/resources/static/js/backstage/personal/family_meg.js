
// 控制详情表单是否禁用
const formDisable = (isDisable) => {
    $('#detailNickname').prop('disabled', isDisable)
    $('#detailIdentity').prop('disabled', isDisable)
    $('#detailCreateTime').prop('disabled', isDisable)
    $('#detailLocked').prop('disabled', isDisable)
    $('#detailControlBackstage').prop('disabled', isDisable)
    $('#detailGender input').prop('disabled', isDisable)
    $('#saveBtn').prop('disabled', isDisable)
    $('#detailPassword').prop('disabled', isDisable)
    if (isDisable) {
        $('#detailPassword').val('*********')
    } else {
        $('#detailPassword').val('')
    }
}

// 获取某个家人的详细信息
const getFamilyDetail = (uid) => {
    return new Promise((resolve, reject) => {
        axios({
            method: "get",
            url: `/user/family/${uid}`,
            headers: {'Authorization': getToken()},
        }).then(resp => {
            if (!resp.data.success || resp.data.data === null) {
                resolve(true)
                return
            }
            let user = resp.data.data;
            $('#detailUid').val(user.uid)
            $('#detailUsername').text(user.username)
            $('#detailNickname').val(user.nickname)
            $('#detailIdentity').val(user.identity)
            $('#detailCreateTime').val(user.createTime)
            $('#detailLocked').prop('checked', user.locked)
            $('#detailControlBackstage').prop('checked', user.controlBackstage)
            if (user.gender === '男') {
                $('#detailGender input:first').prop('checked', true)
            } else {
                $('#detailGender input:last').prop('checked', true)
            }
            formDisable(true)
            resolve(true)
        }).catch(err => reject(err))
    })
}

// 点击修改按钮
const clickUpdate = (uid) => {
    getFamilyDetail(uid).then((success) => {
        if (success) {
            formDisable(false)
            $('#detailNickname').focus()
        } else {
            alert("获取信息失败")
        }
    })
}

// 保存用户信息修改
const updateUser = () => {
    let uid = $('#detailUid').val()
    let nickname = $('#detailNickname').val().trim()
    let identity = $('#detailIdentity').val().trim()
    let password = $('#detailPassword').val()
    if (uid === null || uid === '') {
        alert("页面疑似被篡改，请刷新页面重试")
        return
    }
    if (nickname === null || nickname === '') {
        alert("家人名不能为空")
        return
    }
    if (identity === null || identity === '') {
        alert("家人身份不能为空")
        return
    }
    let pwdReg = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,15}$/
    if (password !== '' && !pwdReg.test(password)) {
        alert("密码复杂度不符合要求")
        return
    }
    let data = {
        uid,
        nickname,
        identity,
        gender: $('#detailGender input:checked').val(),
        locked: $('#detailLocked').prop('checked'),
        controlBackstage: $('#detailControlBackstage').prop('checked')
    }
    if (password !== '') {
        data.password = md5(password)
    }
    console.log(password)
    axios({
        method: "put",
        url: '/user/family/',
        headers: {'Authorization': getToken()},
        data: data
    }).then(resp => {
        if (resp.data.success) {
            alert("修改成功")
            formDisable(true)
        } else {
            alert("系统繁忙，请稍后重试")
        }
    })
}

// 删除家人
const deleteUser = (uid) => {
    let isDelete = confirm("是否确认删除此家人")
    if (!isDelete) {
        return
    }
    axios({
        method: "delete",
        url: `/user/family/${uid}`,
        headers: {'Authorization': getToken()},
    }).then(resp => {
        if (resp.data.success) {
            $('#family' + uid).remove()
        } else {
            alert("删除用户失败，原因可能是：\n" +
                "1. 该用户已被删除\n" +
                "2. 您不能删除自己")
        }
    })
}

// 获取家人列表
const getFamilyList = (isReloadMsg) => {
    let data = {
        username: $('#username').val(),
        nickname: $('#nickname').val(),
        identity: $('#identity').val()
    }
    axios({
        method: "post",
        url: '/user/family/list',
        headers: {'Authorization': getToken()},
        data: data
    }).then(resp => {
        if (!resp.data.success || resp.data.data === null) return
        let html = ''
        let familyList = resp.data.data;
        if (isReloadMsg && familyList[0] != null) {
            getFamilyDetail(familyList[0].uid)
        }
        familyList.forEach(family => {
            html += `<tr id="family${family.uid}">`
            html += `<td>${family.nickname}</td>`
            html += `<td>${family.username}</td>`
            html += `<td>${family.identity}</td>`
            html += '<td>'
            html += `<button type="button" class="btn btn-primary dropdown-toggle" onclick="getFamilyDetail(\'${family.uid}\')">`
            html += '查看'
            html += '</button>&nbsp;'
            html += `<button type="button" class="btn btn-primary dropdown-toggle" onclick="clickUpdate(\'${family.uid}\')">`
            html += '修改'
            html += '</button>&nbsp;'
            html += `<button type="button" class="btn btn-danger dropdown-toggle" onclick="deleteUser(\'${family.uid}\')">`
            html += '删除'
            html += '</button>'
            html += '</td>'
            html += '</tr>'
        })
        $('#familyTbody').html(html)
    })
}
getFamilyList(true)

// 点击查询按钮
$('#searchBtn').click(() => {
    getFamilyList(true)
})

// 点击详情页的保存按钮
$('#saveBtn').click(() => {
    updateUser()
})

// 点击新增按钮
$('#toSavePage').click(() => {
    location.href = '/backstage/personal/add_family.html'
})