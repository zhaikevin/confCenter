var conf = conf || {};

conf.createNS("conf.index");
conf.index = function (options) {
};

conf.index.prototype = {

    init: function () {
        this.initName();
        this.bindEvent();
    },

    initName: function () {
        conf.utils.ajax({
            url: '/user/session',
            type: 'GET',
            ok: function (res, textStatus, jqXHR) {
                if (res.status == 0) {
                    $('#name').after(res.data.userName);
                }
            }
        });
    },

    bindEvent: function () {
        $('.menu').click(function () {
            $('.active').removeClass('active');
            var that = $(this);
            conf.utils.ajax({
                url: '/user/session',
                type: 'GET',
                success: function (res, textStatus, jqXHR) {
                    if (res.status == 0) {
                        $('#content').load(that.attr('url'));
                        that.parent().addClass('active');
                    } else if (res.status == 1) {
                        window.location.href = "/user/login";
                    } else {
                        alert(res.statusInfo);
                    }
                }
            });
        });
    }
};

$(document).ready(function () {
    new conf.index().init();
});