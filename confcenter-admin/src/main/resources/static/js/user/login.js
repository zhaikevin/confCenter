var conf = conf || {};

conf.createNS("conf.app.member.login");
conf.app.member.login = function (options) {
};

conf.app.member.login.prototype = {

    init: function () {
        this.bindButtonEvent();
    },

    bindButtonEvent: function () {
        var that = this;
        $('#login').click(function () {
            that.login();
        });
    },

    login: function () {
        var userName = $('#userName').val().trim();
        var password = $('#password').val().trim();
        if(userName.isEmpty()) {
            alert('用户名不能为空');
            return false;
        }
        if(password.isEmpty()) {
            alert('密码不能为空');
            return false;
        }
        var data = {
            'userName':userName,
            'password':password
        }
        conf.utils.ajax({
            url: '/user/login',
            type: 'POST',
            async: false,
            data: data,
            ok: function (res, textStatus, jqXHR) {
                if (res.data) {
                    alert('登录成功');
                } else {
                    alert(res.statusInfo);
                }
            }
        });
    }
};

$(document).ready(function () {
    new conf.app.member.login().init();
});