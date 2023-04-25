// 获取设备分组的名字列表
const getEquipGroupNames = () => {
    axios({
        method: "get",
        url: '/equipGroup/names',
        headers: {'Authorization': getToken()},
    }).then(resp => {
        if (!resp.data.success || resp.data.data === null) return
        let html = '<option value="-1" disabled selected hidden>请选择</option>'
        resp.data.data.forEach(group => {
            html += `<option value="${group.gid}" style="color: black;">${group.groupName}</option>`
        })
        $('#equipGroupNames').html(html)
    })
}
getEquipGroupNames()

$('#saveBtn').click(() => {
    let equipName = $('#equipName').val();
    let equipGroupName = $('#equipGroupNames option:selected').val();
    let equipCode = $('#equipCode').val();
    let equipCodeReg = /^\w{15}$/
    if (!equipCodeReg.test(equipCode)) {
        alert("请检查设备码格式是否正确!")
        return
    }
    if (equipName === '') {
        alert("设备名不能为空!")
        return
    }
    if (equipGroupName === '-1') {
        alert("请选择设备分组!")
        return
    }
    let data = {
        equipCode: equipCode,
        equipName: equipName,
        groupId: equipGroupName,
        main: $('#main').prop('checked'),
        showed: $('#showed').prop('checked'),
        locked: $('#locked').prop('checked')
    }
    axios({
        method: "post",
        url: '/equipment',
        headers: {'Authorization': getToken()},
        data: data
    }).then(resp => {
        if (resp.data.success) {
            location.href = '/backstage/device/device_list.html'
        } else {
            alert('设备添加失败，请检查设备码或者其他参数是否错误')
        }
    })
})

// 只要点击设备为主设备，则自动选中在主页中展示
$('#main').click(() => {
    let isMain = $('#main').prop('checked')
    if (isMain) {
        $('#showed').prop('checked', true)
    }
})