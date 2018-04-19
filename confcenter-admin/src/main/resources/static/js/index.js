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
        $('.nav').click(function () {
            $('#main-content').load($(this).attr("url"));
        });
    }
};

$(document).ready(function () {
    new conf.index().init();
});