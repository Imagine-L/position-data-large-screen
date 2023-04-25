// 验证用户名
const checkUsername = () => {
    let $username = $('#username')
    let tip = $('#usernameTip');
    let username = $username.val();
    if (username === null || username === '') {
        // alert("dad")
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
    let $pwd = $('#password')
    let tip = $('#passwordTip');
    // 密码必须由数字、字母两种字符组成，长度在8-15位之间
    let reg = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,15}$/;
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

const saveUser = () => {
    if (!checkPwd()) {
        alert("密码不符合要求")
        return
    }
    let nickname = $('#nickname').val().trim()
    let identity = $('#identity').val().trim()
    let password = $('#password').val()
    let $gender = $('#gender input:checked')
    if (nickname === '') {
        alert('家人名不能为空')
        return
    }
    if (identity === '') {
        alert('家人身份不能为空')
        return
    }
    if ($gender.length !== 1) {
        alert("请选择家人性别")
        return
    }
    checkUsername().then(success => {
        if (!success) {
            alert("用户名不符合要求")
            return
        }
        let data = {
            username: $('#username').val(),
            password: md5(password),
            nickname: nickname,
            identity: identity,
            gender: $gender.val(),
            locked: $('#locked').prop('checked'),
            controlBackstage: $('#controlBackstage').prop('checked')
        }
        axios({
            method: "post",
            url: '/user/family',
            headers: {'Authorization': getToken()},
            data: data
        }).then(resp => {
            if (resp.data.success) {
                location.href = '/backstage/personal/family_meg.html'
            } else {
                alert("系统繁忙，请稍后重试")
            }
        })
    })
}

$('#saveBtn').click(() => {
    saveUser()
})

$('#username').blur(() => {
    checkUsername()
})

$('#password').blur(() => {
    checkPwd()
})
