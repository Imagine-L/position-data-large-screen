let groupParams = {
    groupName: '',
    description: '',
    count: ''
}

// 是否禁用分组信息表单
const formDisabled = (isDisable) => {
    $('#groupNameInput').prop('disabled', isDisable)
    $('#groupDescription').prop('disabled', isDisable)
    $('#equipCheckbox input:not(:checked)').prop('disabled', isDisable)
    $('#equipCheckbox input:checked').prop('disabled', true)
    // console.log($('#equipCheckbox input:checked'))
    $('#showed').prop('disabled', isDisable)
    $('#locked').prop('disabled', isDisable)
    $('#saveBtn').prop('disabled', isDisable)
}

// 点击移出分组按钮
const clickMove = (equipCode) => {
    $('#equipGroupNames option:first').prop('selected', true)
    $('#currentEquipment').text(equipCode)
    $('#moveModal').modal('show')
}

// 确定移动分组
const equipmentMoveGroup = () => {
    let targetGroupId = $('#equipGroupNames option:selected').val()
    let currentGroupId = $('#groupId').val()
    let equipCode = $('#currentEquipment').text();
    if (targetGroupId === '-1') {
        alert("请选择分组！")
        return
    }
    if (targetGroupId === currentGroupId) {
        $('#moveModal').modal('hide')
        alert("修改成功")
        return
    }
    let data = {
        equipCode: equipCode,
        groupId: targetGroupId
    }
    axios({
        method: "put",
        url: '/equipment',
        headers: {'Authorization': getToken()},
        data: data
    }).then(resp => {
        if (resp.data.success) {
            $('#moveModal').modal('hide')
            // 修改目标组设备数量
            let $targetGroupCount = $('#group' + targetGroupId + ' td:eq(2)')
            let targetOldCount = $targetGroupCount.text()
            $targetGroupCount.text(Number.parseInt(targetOldCount) + 1)
            // 修改当前组设备数量
            let $currentGroupCount = $('#group' + currentGroupId + ' td:eq(2)')
            let currentOldCount = $currentGroupCount.text()
            let currentNewCount = Number.parseInt(currentOldCount) - 1
            $currentGroupCount.text(currentNewCount)
            $('#countText').text(currentNewCount)
            // 移除当前详情页的设备
            $('#equipment' + equipCode).remove()
            // 修改详情页复选框设备的选择按钮
            $('#equipCheckbox' + equipCode).prop('checked', false)
            alert("修改成功")
        } else {
            alert("系统繁忙，请稍后重试")
        }
    })
}

// 解析设备列表，并显示到屏幕上
const solveEquipments = (equipments, groupName) => {
    $('#currentGroup').text(groupName)
    if (equipments === null) return
    let html = ''
    equipments.forEach(equipment => {
        html += `<tr id="equipment${equipment.equipCode}">`
        html += `<td>${equipment.equipName}</td>`
        html += `<td>${groupName}</td>`
        html += `<td>${equipment.equipCode}</td>`
        html += '<td>'
        html += `<button type="button" class="btn btn-danger dropdown-toggle" onclick="clickMove(\'${equipment.equipCode}\')">`
        html += '移出本组'
        html += '</button>'
        html += '</td>'
        html += '</tr>'
    })
    $('#equipmentTbody').html(html)
}

// 查看某个分组的详细信息，由于获取分组之后可能还要执行其他操作，
// 所以我们返回一个Promise对象
const getGroupDetail = (gid) => {
    return new Promise((resolve, reject) => {
        axios({
            method: "get",
            url: `/equipGroup/${gid}`,
            headers: {'Authorization': getToken()}
        }).then(resp => {
            if (!resp.data.success) {
                resolve(false)
                return
            }
            let equipGroup = resp.data.data;
            $('#groupId').val(equipGroup.gid)
            $('#groupNameInput').val(equipGroup.groupName)
            $('#groupDescription').val(equipGroup.description)
            $('#countText').text(equipGroup.count)
            $('#createTime').text(equipGroup.createTime)
            $('#showed').prop('checked', equipGroup.equipmentsShowed)
            $('#locked').prop('checked', equipGroup.equipmentsLocked)
            let equipments = equipGroup.equipments;
            solveEquipments(equipments, equipGroup.groupName)
            $('.equipmentNames').each((index, checkbox) => {
                $(checkbox).prop('checked', false)
                for (let equipment of equipments) {
                    if (equipment.eid === $(checkbox).val()) {
                        $(checkbox).prop('checked', true)
                    }
                }
            })
            formDisabled(true)
            resolve(true)
        }).catch(err => reject(err))
    })
}

// 点击修改按钮，让表单能输入
const clickUpdate = (gid) => {
    getGroupDetail(gid).then((success) => {
        if (success) {
            formDisabled(false)
            $('#groupNameInput').focus()
        } else {
            console.log("获取详细信息失败")
        }
    })
}

// 更新分组
const updateGroup = () => {
    let gid = $('#groupId').val()
    let groupName = $('#groupNameInput').val()
    if (gid === null || gid === '') {
        alert("修改失败，可能是因为页面被恶意篡改")
        return
    }
    if (groupName === null || groupName === '') {
        alert("分组名不能为空")
        return
    }
    let ids = []
    $('#equipCheckbox input:checked').each(function () {
        ids.push($(this).val())
    })
    let data = {
        gid: gid,
        groupName: groupName,
        description: $("#groupDescription").val(),
        equipmentIds: ids,
        equipmentsShowed: $("#showed").prop('checked'),
        equipmentsLocked: $('#locked').prop('checked')
    }

    axios({
        method: "put",
        url: '/equipGroup',
        headers: {'Authorization': getToken()},
        data: data
    }).then(resp => {
        if (!resp.data.success) {
            alert("系统繁忙，请稍后重试")
            return
        }
        formDisabled(true)
        getGroupList(false)
        getGroupDetail(gid)
        alert("修改成功")
    })
}

// 删除分组
const deleteGroup = (gid) => {
    let isDelete = confirm(`是否确认删除该设备？`)
    if (!isDelete) {
        return
    }
    axios({
        method: "delete",
        url: `/equipGroup/${gid}`,
        headers: {'Authorization': getToken()},
    }).then(resp => {
        if (resp.data.success) {
            $('#group' + gid).remove()
            $('#groupOption' + gid).remove()
        } else {
            alert("请检查分组中是否还有设备")
        }
    })
}

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
            html += `<input type="checkbox" class="equipmentNames" id="equipCheckbox${equipment.equipCode}" value="${equipment.eid}" disabled>`
            html += equipment.equipName
            html += '</label>'
            html += '</div>'
        })
        $('#equipCheckbox').html(html)
    })
}
getEquipmentsNames()

// 查看分组列表
const getGroupList = (isReloadMsg) => {
    groupParams.groupName = $('#groupName').val()
    groupParams.description = $('#description').val()
    groupParams.count = $('#count').val()
    let countReg = /^0$|^[1-9]\d*$/
    // 如果数量不为空，就要进行正则数字校验，如果校验结果为false，则弹出提示并结束请求
    if (groupParams.count !== '' && !countReg.test(groupParams.count)) {
        alert("设备数量参数非法")
        return
    }
    axios({
        method: "post",
        url: '/equipGroup/list',
        headers: {'Authorization': getToken()},
        data: groupParams
    }).then(resp => {
        if (!resp.data.success || resp.data.data === null) return
        let groupTbodyHtml = ''
        let groupOptionHtml = '<option value="-1" disabled="" selected="" hidden="">请选择新分组</option>'
        if (isReloadMsg && resp.data.data.length > 0) {
            let group = resp.data.data[0]
            getGroupDetail(group.gid)
        }
        resp.data.data.forEach(group => {
            // 封装分组列表栏的html
            groupTbodyHtml += `<tr id="group${group.gid}">`
            groupTbodyHtml += `<td>${group.groupName}</td>`
            groupTbodyHtml += `<td>${group.description}</td>`
            groupTbodyHtml += `<td>${group.count}</td>`
            groupTbodyHtml += '<td>'
            groupTbodyHtml += `<button type="button" class="btn btn-primary dropdown-toggle" onclick="getGroupDetail(\'${group.gid}\')">`
            groupTbodyHtml += '查看'
            groupTbodyHtml += '</button>&nbsp;'
            groupTbodyHtml += `<button type="button" class="btn btn-primary dropdown-toggle" onclick="clickUpdate(\'${group.gid}\')">`
            groupTbodyHtml += '修改'
            groupTbodyHtml += '</button>&nbsp;'
            groupTbodyHtml += `<button type="button" class="btn btn-danger dropdown-toggle" onclick="deleteGroup(\'${group.gid}\')">`
            groupTbodyHtml += '删除'
            groupTbodyHtml += '</button>'
            groupTbodyHtml += '</td>'
            groupTbodyHtml += '</tr>'
            // 封装移出本组遮罩层的html
            groupOptionHtml += `<option id="groupOption${group.gid}" value="${group.gid}" style="color: black;">${group.groupName}</option>`
        })
        $('#groupTbody').html(groupTbodyHtml)
        $('#equipGroupNames').html(groupOptionHtml)
    })
}
getGroupList(true)

// 点击查询按钮
$('#searchBtn').click(() => {
    getGroupList(true)
})

// 点击移动分组的确认按钮
$('#moveBtn').click(() => {
    equipmentMoveGroup()
})

// 更新操作中的保存按钮
$('#saveBtn').click(() => {
    updateGroup()
})

// 点击新增按钮
$('#toSavePage').click(() => {
    location.href = '/backstage/device/add_group.html'
})