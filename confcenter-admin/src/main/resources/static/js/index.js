var conf = conf || {};

conf.createNS("conf.app.index");
conf.app.index = function (options) {
};

conf.app.index.prototype = {

    init: function () {
        this.initName();
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
    }
};

$(document).ready(function () {
    new conf.app.index().init();
});