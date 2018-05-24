var conf = conf || {};

conf.createNS("conf.project.list");
conf.project.list = function (options) {
};

conf.project.list.prototype = {

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
        conf.project.table.ajax.reload();
    },

    initTable: function () {
        conf.loaded = true;
        conf.project.table = $('#project-table').DataTable({
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
            ajax: conf.pagination('/project/list', $('#searchForm')),
            columns: [
                {"data": "id"},
                {"data": "projectName"},
                {"data": "projectDesc"},
                {"data": "status"},
                {"data": "createTime"},
                {"data": "creatorName"}
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
                    targets: [3], data: "status", render: function (data, type, row) {
                    if (data == 1) {
                        return '有效';
                    } else {
                        return '无效';
                    }
                }
                },
                {
                    targets: [4], data: "createTime", render: function (data, type, row) {
                    return conf.utils.formatDate(data, 'yyyy-MM-dd hh:mm:ss');
                }
                }
            ]
        });
    }
};

$(document).ready(function () {
    new conf.project.list().init();
});