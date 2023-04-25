let $username = $("#username")
let $pwd = $("#password")
let $equipCode = $("#equipCode")
let $errorTips = $("#errorTips")

$("#submit").click(() => {
    // 表单验证
    if (!(checkEquipCode() && checkPwd() && checkRePwd())) {
        $errorTips.css("display", "flex");
        $errorTips.html("<p>请检查密码是否合法!</p>")
        return
    }
    checkUsername().then(res => {
        if (!res) {
            $errorTips.css("display", "flex");
            $errorTips.html("<p>请检查您输入的用户名是否重复!</p>")
            return
        }
        /// 如果不想让用户注册，则加上这一段代码
        // $errorTips.css("display", "flex");
        // $errorTips.html("<p>请检查机器码是否输入错误!</p>")
        // return;
        // 封装表单
        let registerParams = {
            username: $username.val(),
            password: md5($pwd.val()),
            equipCode: $equipCode.val()
        }
        // 发起请求
        axios({
            method: "POST",
            url: '/register',
            data: registerParams,
        }).then(resp => {
            console.log(resp)
            if (resp.data.success) {
                storageUtils.LOCAL.saveObject(storageConstant.CURRENT_USER_TAG, resp.data.data)
                location.href = "../index.html"
            } else {
                $errorTips.css("display", "flex");
                $errorTips.html(`<p>注册失败，请检查设备码等信息是否出错！</p>`)
            }
        })
    })
})

function hideError() {
    $errorTips.css("display", "none");
    $username.val("");
    $pwd.val("")
}

// 验证用户名
function checkUsername() {
    let tip = $username.next();
    let username = $username.val();
    if (username === null || username === '') {
        tip.text("请输入用户名");
        $username.css("borderColor", "red");
        tip.addClass("error");
        tip.removeClass("ok");
        return new Promise(resolve => resolve(false))
    }
    return new Promise((resolve, reject) => {
        axios({
            method: "GET",
            url: `/checkUser/${username}`,
        }).then(resp => {
            // 结果为false表示用户名不重复，验证成功
            if (!resp.data.data) {
                tip.text("用户名验证成功");
                $username.css("borderColor", "green");
                tip.addClass("ok");
                tip.removeClass("error");
                resolve(true)
            } else {
                tip.text("用户名已重复");
                $username.css("borderColor", "red");
                tip.addClass("error");
                tip.removeClass("ok");
                resolve(false)
            }
        })
    })
}


// 验证输入密码
function checkPwd() {
    // 密码必须由数字、字母两种字符组成，长度在8-15位之间
    let reg = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,15}$/;
    let tip = $pwd.next();
    if (reg.test($pwd.val())) {
        tip.text("密码强度合格");
        $pwd.css("borderColor", "green");
        tip.addClass("ok");
        tip.removeClass("error");
        return true;
    } else {
        tip.text("密码强度过低");
        $pwd.css("borderColor", "red");
        tip.addClass("error");
        tip.removeClass("ok");
        return false;
    }
}

// 验证重复输入密码
function checkRePwd() {
    let $repwd = $("#repwd");
    let tip = $repwd.next();
    if ($repwd.val() === $pwd.val()) {
        tip.text("重复输入正确");
        $repwd.css("borderColor", "green");
        tip.addClass("ok");
        tip.removeClass("error");
        return true;
    } else {
        tip.text("密码输入不一致");
        $repwd.css("borderColor", "red");
        tip.addClass("error");
        tip.removeClass("ok");
        return false;
    }
}

// 验证邮箱
function checkEquipCode() {
    // let reg = /^[0-9a-zA-Z_.-]+[@][0-9a-zA-Z_.-]+([.][a-zA-Z]+){1,2}$/;
    let reg = /^\w{15}$/
    let tip = $equipCode.next();
    if (reg.test($equipCode.val())) {
        // tip.text("邮箱格式正确");
        tip.text('机器码格式正确')
        $equipCode.css("borderColor", "green");
        tip.addClass("ok");
        tip.removeClass("error");
        return true;
    } else {
        // tip.text("邮箱格式错误");
        tip.text('机器码格式错误')
        $equipCode.css("borderColor", "red");
        tip.addClass("error");
        tip.removeClass("ok");
        return false;
    }
}