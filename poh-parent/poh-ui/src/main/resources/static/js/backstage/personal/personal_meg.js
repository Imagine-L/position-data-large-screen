let currentUser = getCurrentUser()
$('#username').text(currentUser.username)
$('#nickname').val(currentUser.nickname)
$('#email').val(currentUser.email)
if (currentUser.gender === '男') {
    $('#gender input:first').prop('checked', true)
} else {
    $('#gender input:last').prop('checked', true)
}

// 更新用户信息
const updateUser = () => {
    let nickname = $('#nickname').val()
    let email = $('#email').val()
    if (nickname === null || nickname === '') {
        alert("昵称不能为空")
        return
    }
    // 校验邮箱
    let emailReg = /^[0-9a-zA-Z_.-]+[@][0-9a-zA-Z_.-]+([.][a-zA-Z]+){1,2}$/;
    if (!emailReg.test(email)) {
        alert("邮箱格式错误")
        return;
    }
    let data = {
        nickname,
        email,
        gender: $('#gender input:checked').val()
    }

    axios({
        method: "put",
        url: '/user',
        headers: {'Authorization': getToken()},
        data: data
    }).then(resp => {
        if (resp.data.success) {
            alert("修改成功")
        } else {
            alert("信息修改失败，您可能没有操作权限")
        }
    })
}

const updatePwd = () => {
    let oldPwd = $('#oldPwd').val()
    let newPwd = $('#newPwd').val()
    let retryPwd = $('#retryPwd').val()
    if (oldPwd === null || oldPwd === '') {
        alert('请输入原密码')
        return
    }
    if (newPwd === null || newPwd === '') {
        alert("请输入新密码")
        return
    }
    if (retryPwd === null || retryPwd === '') {
        alert("请确认新密码")
        return
    }
    if (oldPwd === newPwd) {
        alert("您输入的新密码与旧密码一致")
        return
    }
    if (newPwd !== retryPwd) {
        alert("新密码两次密码输入不一致")
        return
    }
    // 密码强度校验
    // 密码必须由数字、字母两种字符组成，长度在8-15位之间
    let pwdReg = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,15}$/;
    if (!pwdReg.test(newPwd)) {
        alert("密码强度过低，请重新输入")
        $('#newPwd').val('')
        $('#retryPwd').val('')
        return
    }
    let data = {
        oldPwd: md5(oldPwd),
        newPwd: md5(newPwd)
    }
    axios({
        method: "put",
        url: '/user/pwd',
        headers: {'Authorization': getToken()},
        data: data
    }).then(resp => {
        if (resp.data.success) {
            alert("密码修改成功")
            $('#oldPwd').val('')
            $('#newPwd').val('')
            $('#retryPwd').val('')
        } else {
            alert("密码修改失败，原因可能是：\n" +
                "1. 原密码错误\n" +
                "2. 新密码和原密码一致，无需修改\n" +
                "3. 您没有操作权限")
        }
    })
}

$('#updatePersonalBtn').click(() => {
    updateUser()
})
$('#updatePwdBtn').click(() => {
    updatePwd()
})