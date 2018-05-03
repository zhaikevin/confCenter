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
            $('#content').load($(this).attr('url'));
            // var url = $(this).attr("url");
            // $.get(url, function (data) {
            //     $("#content").html(data);//初始化加载界面
            // });
            $(this).parent().addClass('active');
        });
    }
};

$(document).ready(function () {
    new conf.index().init();
});