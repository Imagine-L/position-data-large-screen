// 判断当前地址是否为后台地址
let isBackstage = location.pathname.includes('backstage')

const returnPage = () => {
    location.href = isBackstage ? "../../index.html" : "/login.html"
}

// 先判断是否本地含有token
let user = getCurrentUser()
if (user === null || user.token === null) {
    returnPage()
}
// 是后台地址，但是用户不允许前往后台，则直接返回页面
if (isBackstage && !user.toBackstage) {
    returnPage()
}

// 从服务端更新token
const updateToken = () => {
    axios({
        method: "GET",
        url: '/user/update/info',
        headers: {'Authorization': user.token}
    }).then(resp => {
        if (resp.data.success) {
            storageUtils.LOCAL.saveObject(storageConstant.CURRENT_USER_TAG, resp.data.data)
            // 判断是否为后台地址
            if (isBackstage) {
                let currUser = resp.data.data
                // 如果为后台地址，但是没有后台权限，则跳转回主页
                if (!currUser.toBackstage) {
                    storageUtils.LOCAL.remove(storageConstant.CURRENT_USER_TAG)
                    returnPage()
                }
            }
        } else {
            storageUtils.LOCAL.remove(storageConstant.CURRENT_USER_TAG)
            returnPage()
        }
    })
}

updateToken()


// // 如果为后台地址，并且用户没有前往后台的权限，则返回
// if (isBackstage && !user.toBackstage) {
//     returnPage()
// } else {
//     // 前台地址，和不满足上面条件的地址，需要更新token后执行判断
//
// }


