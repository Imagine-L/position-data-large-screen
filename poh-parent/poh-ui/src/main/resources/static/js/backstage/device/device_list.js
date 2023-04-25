const equipmentParams = {
    equipCode: '',
    groupName: '',
    equipName: ''
}
// 设备信息表单开启与禁用
const formDisabled = (isDisabled) => {
    $('#deviceName').prop('disabled', isDisabled)
    $('#equipGroupNames').prop('disabled', isDisabled)
    $('#main').prop('disabled', isDisabled)
    $('#showed').prop('disabled', isDisabled)
    $('#locked').prop('disabled', isDisabled)
    $('#saveBtn').prop('disabled', isDisabled)
}
// 获取设备分组的名字列表
const getEquipGroupNames = () => {
    axios({
        method: "get",
        url: '/equipGroup/names',
        headers: {'Authorization': getToken()}
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

// 查询某个设备的详细信息，由于可能有后续操作，所以我们返回Promise对象
const getDetailEquipment = (eid) => {
    return new Promise((resolve, reject) => {
        axios({
            method: "get",
            url: `/equipment/${eid}`,
            headers: {'Authorization': getToken()}
        }).then(resp => {
            if (!resp.data.success || resp.data.data === null) {
                resolve(false)
                return;
            }
            let equipment = resp.data.data
            console.log(equipment)
            $('#deviceName').val(equipment.equipName)
            $('#deviceCode').text(equipment.equipCode)
            $('#version').text(equipment.version)
            $('#type').text(equipment.type)
            $('#equipGroupNames option').each((index, option) => {
                if ($(option).val() === equipment.groupId) {
                    $(option).prop('selected', true)
                }
            })
            $('#batteryCapacity').text(equipment.batteryCapacity)
            $('#showed').prop('checked', equipment.showed)
            $('#main').prop('checked', equipment.main)
            $('#locked').prop('checked', equipment.locked)
            formDisabled(true)
            resolve(true)
        }).catch(err => reject(err))
    })
}

// 点击修改按钮
const clickUpdate = (eid) => {
    getDetailEquipment(eid).then((success) => {
        if (success) {
            formDisabled(false)
            $('#deviceName').focus()
        } else {
            console.log("获取详细信息失败")
        }
    })
}

// 保存修改的设备信息
const updateEquipment = () => {
    let equipName = $('#deviceName').val();
    if (equipName === '') {
        alert("设备名不能为空!")
        return
    }
    let equipGroupName = $('#equipGroupNames option:selected').val();
    if (equipGroupName === '-1') {
        alert("请选择设备分组!")
        return
    }
    let data = {
        equipCode: $('#deviceCode').text(),
        equipName: equipName,
        groupId: equipGroupName,
        main: $('#main').prop('checked'),
        showed: $('#showed').prop('checked'),
        locked: $('#locked').prop('checked')
    }
    axios({
        method: "put",
        url: '/equipment',
        headers: {'Authorization': getToken()},
        data: data
    }).then(resp => {
        if (!resp.data.success) {
            alert("系统繁忙，请稍后重试")
            return
        }
        formDisabled(true)
        getEquipmentList(false);
        alert("修改成功")
    })
}

// 删除设备
const deleteEquipment = (eid) => {
    let isDelete = confirm(`是否确认删除该设备？`)
    if (!isDelete) {
        return
    }
    axios({
        method: "delete",
        url: `/equipment/${eid}`,
        headers: {'Authorization': getToken()},
    }).then(resp => {
        if (resp.data.success) {
            $('#' + eid).remove()
        } else {
            alert("系统繁忙，请稍后重试")
        }
    })
}

// 查询设备列表
const getEquipmentList = (isReloadDetail) => {
    equipmentParams.equipName = $('#deviceNameSearch').val()
    equipmentParams.groupName = $('#deviceGroupSearch').val()
    equipmentParams.equipCode = $('#deviceIdSearch').val()
    axios({
        method: "post",
        url: '/equipment/list',
        headers: {'Authorization': getToken()},
        data: equipmentParams
    }).then(resp => {
        if (!resp.data.success || resp.data.data === null) return
        let html = ''
        if (isReloadDetail && resp.data.data.length > 0) {
            getDetailEquipment(resp.data.data[0].eid)
        }
        resp.data.data.forEach(equipment => {
            html += `<tr id="${equipment.eid}">`
            /// 原定计划是在表格中多显示一行不显示的id，但是后面好像发现不用也可以，留着怕后面有用
            // html += `<td style="display: none">${equipment.eid}</td>`
            html += `<td>${equipment.equipName}</td>`
            html += `<td>${equipment.groupName}</td>`
            html += `<td>${equipment.equipCode}</td>`
            html += '<td>'
            html += `<button type="button" class="btn btn-primary dropdown-toggle" onclick="getDetailEquipment(\'${equipment.eid}\')">`
            html += '查看'
            html += '</button>&nbsp;'
            html += `<button type="button" class="btn btn-primary dropdown-toggle" onclick="clickUpdate(\'${equipment.eid}\')" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">`
            html += '修改'
            html += '</button>&nbsp;'
            html += `<button type="button" class="btn btn-danger dropdown-toggle" onclick="deleteEquipment(\'${equipment.eid}\')" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">`
            html += '删除'
            html += '</button>&nbsp;'
            html += '</td>'
            html += '</tr>'
        })
        $('#deviceTbody').html(html)
    })
}
getEquipmentList(true)


// 单击查询按钮再次进行查询
$('#search').click(() => {
    getEquipmentList(true)
})

// 点击保存按钮
$('#saveBtn').click(() => {
    updateEquipment()
})

// 点击新增按钮
$('#toSavePage').click(() => {
    location.href = '/backstage/device/add_device.html'
})

// 只要点击设备为主设备，则自动选中在主页中展示
$('#main').click(() => {
    let isMain = $('#main').prop('checked')
    if (isMain) {
        $('#showed').prop('checked', true)
    }
})