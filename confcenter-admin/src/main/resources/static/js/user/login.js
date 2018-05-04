var conf = conf || {};

conf.createNS("conf.member.login");
conf.member.login = function (options) {
};

conf.member.login.prototype = {

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
            $.fn.alert('用户名不能为空');
            return false;
        }
        if(password.isEmpty()) {
            $.fn.alert('密码不能为空');
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
                if (res.status == 0) {
                    window.location.href = "/index";
                } else {
                    $.fn.alert(res.statusInfo);
                }
            }
        });
    }
};

$(document).ready(function () {
    new conf.member.login().init();
});