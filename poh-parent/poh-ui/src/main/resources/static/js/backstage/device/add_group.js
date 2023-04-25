// 查询全部的设备名字
const getEquipmentsNames = () => {
    axios({
        method: "get",
        url: '/equipment/names',
        headers: {'Authorization': getToken()},
    }).then(resp => {
        if (!resp.data.success) return
        let equipments = resp.data.data;
        let html = ''
        equipments.forEach(equipment => {
            html += '<div class="checkbox">'
            html += '<label>'
            html += `<input type="checkbox" class="equipmentNames" value="${equipment.eid}">`
            html += equipment.equipName
            html += '</label>'
            html += '</div>'
        })
        $('#equipCheckbox').html(html)
    })
}
getEquipmentsNames()

$('#saveBtn').click(() => {
    let groupName = $('#groupName').val()
    if (groupName === null || groupName === '') {
        alert("分组名不能为空")
        return
    }
    let ids = []
    $('#equipCheckbox input:checked').each(function () {
        ids.push($(this).val())
    })
    let data = {
        groupName: groupName,
        description: $("#description").val(),
        equipmentIds: ids,
        equipmentsShowed: $("#showed").prop('checked'),
        equipmentsLocked: $('#locked').prop('checked')
    }

    axios({
        method: "post",
        url: '/equipGroup',
        headers: {'Authorization': getToken()},
        data: data
    }).then(resp => {
        if (resp.data.success) {
            location.href = '/backstage/device/device_group_list.html'
        } else {
            alert("系统繁忙，请稍后重试")
        }
    })
})