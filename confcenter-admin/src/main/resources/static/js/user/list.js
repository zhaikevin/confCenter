var conf = conf || {};

conf.createNS("conf.member.list");
conf.member.list = function (options) {
};

conf.member.list.prototype = {

    init: function () {
        this.bindEvent();
        this.initTable();
    },

    bindEvent: function () {
        var that = this;
        $('#search').click(function () {
            that.reload();
        });
    },

    reload: function () {
        conf.loaded = true;
        conf.member.table.ajax.reload();
    },

    initTable: function () {
        conf.loaded = true;
        conf.member.table = $('#member-table').DataTable({
            bAutoWidth: false,
            responsive: true,
            iDisplayLength: 30,
            aLengthMenu: [10, 20, 30, 40, 50],
            oLanguage: {
                sUrl: '/js/language/Chinese.lang'
            },
            bProcessing: false,
            bServerSide: true,
            bFilter: false,
            bSort: false,
            ajax: conf.pagination('/user/list', $('#searchForm')),
            columns: [
                {"data": "id"},
                {"data": "userName"},
                {"data": "type"},
                {"data": "status"},
                {"data": "mail"},
                {"data": "createTime"}
            ],
            columnDefs: [
                {
                    targets: '_all',
                    class: 'center'
                },
                {
                    targets: [0], data: "id", render: function (data, type, row) {
                    return '<input name="id" type="radio" class="id-radio" onclick="" value="' + data + '"/>';
                }
                },
                {
                    targets: [2], data: "type", render: function (data, type, row) {
                    if (data == 1) {
                        return '管理员账户';
                    } else {
                        return '普通账户';
                    }
                }
                },
                {
                    targets: [3], data: "status", render: function (data, type, row) {
                    if (data == 1) {
                        return '有效';
                    } else {
                        return '无效';
                    }
                }
                },
                {
                    targets: [5], data: "createTime", render: function (data, type, row) {
                    return conf.utils.formatDate(data, 'yyyy-MM-dd hh:mm:ss');
                }
                }
            ]
        });
    }
};

$(document).ready(function () {
    new conf.member.list().init();
});