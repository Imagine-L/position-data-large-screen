let $username = $("#username");
let $pwd = $("#password");
let $errorTips = $("#errorTips");

$('#submit').click(() => {
    // 表单验证
    if (contentIsNull()) {
        $errorTips.css("display", "flex");
        $errorTips.html("<p>请检查您的输入是否正确!</p>")
        return
    }
    // 封装表单
    let loginParams = {
        username: $username.val(),
        password: md5($pwd.val())
    }
    // 发起请求
    axios({
        method: "POST",
        url: '/login',
        data: loginParams,
    }).then(resp => {
        if (resp.data.success) {
            storageUtils.LOCAL.saveObject(storageConstant.CURRENT_USER_TAG, resp.data.data)
            location.href = "../index.html"
        } else {
            $errorTips.css("display", "flex");
            $errorTips.html(`<p>${resp.data.message}!</p>`)
        }
    })
})

$('#touristsLogin').click(() => {
    // 封装表单
    let loginParams = {
        username: 'account',
        password: md5('123456')
    }
    // 发起请求
    axios({
        method: "POST",
        url: '/login',
        data: loginParams,
    }).then(resp => {
        if (resp.data.success) {
            storageUtils.LOCAL.saveObject(storageConstant.CURRENT_USER_TAG, resp.data.data)
            location.href = "../index.html"
        } else {
            $errorTips.css("display", "flex");
            $errorTips.html(`<p>${resp.data.message}!</p>`)
        }
    })
})

let contentIsNull = () => {
    let flag = false
    if ($username.val() !== "") {
        $username.next().css("color", "green");
        $username.css("borderColor", "green");
    } else {
        $username.next().css("color", "red");
        $username.css("borderColor", "red");
        flag = true
    }

    if ($pwd.val() !== "") {
        $pwd.next().css("color", "green");
        $pwd.css("borderColor", "green");
    } else {
        $pwd.next().css("color", "red");
        $pwd.css("borderColor", "red");
        flag = true
    }
    return flag
}

function hideError() {
    $errorTips.css("display", "none");
    $username.val("");
    $pwd.val("")
}

$username.blur(contentIsNull)
$pwd.blur(contentIsNull)