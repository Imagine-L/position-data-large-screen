$(function () {
    // 退出登录事件
    $('#logout').click(() => {
        // 发起请求
        axios({
            method: "get",
            url: '/logout',
            headers: {'Authorization': getToken() }
        }).then((resp) => {
            console.log("登出成功")
        })
        storageUtils.LOCAL.remove(storageConstant.CURRENT_USER_TAG)
        location.href = "../../login.html"
    })
})