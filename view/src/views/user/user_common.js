import Util from '@/libs/util';

let userCommon = {};
userCommon.tableColumns = [
    {
        title: '序号',
        width: 80,
        align: 'center',
        type: 'index'
    },
    {
        title: '用户姓名',
        key: 'userName',
        align: 'center'
    },
    {
        title: '账户类型',
        key: 'type',
        align: 'center',
        render: (h, params) => {
            return h('div', userCommon.formatType(params.row.type));
        }
    },
    {
        title: '状态',
        key: 'status',
        align: 'center',
        render: (h, params) => {
            return h('div', userCommon.formatStatus(params.row.status));
        }
    },
    {
        title: '邮箱',
        key: 'mail',
        align: 'center'
    },
    {
        title: '新建时间',
        key: 'createTime',
        align: 'center',
        render: (h, params) => {
            return h('div', Util.formatDate(params.row.createTime));
        }
    },
    {
        title: '操作',
        align: 'center',
        key: 'handle',
        width: 300,
        handle: [
            {
                type: 'edit',
                functions: ['on-edit']
            },
            {
                type: 'delete',
                name: '禁用',
                content: '您确定要禁用该用户吗?',
                functions: ['on-disable']
            },
            {
                type: 'delete',
                name: '启用',
                content: '您确定要启用该用户吗?',
                buttonType: 'info',
                functions: ['on-enable']
            }]
    }
];

userCommon.statusList = [
    {
        value: 0,
        label: '禁用'
    },
    {
        value: 1,
        label: '有效'
    }
];

userCommon.typeList = [
    {
        value: 1,
        label: '管理员账户'
    },
    {
        value: 2,
        label: '普通账户'
    }
];

userCommon.formatStatus = function (status) {
    var statusList = userCommon.statusList;
    for (var obj in statusList) {
        if (statusList[obj].value === status) {
            return statusList[obj].label;
        }
    }
    return '';
};

userCommon.formatType = function (type) {
    var typeList = userCommon.typeList;
    for (var obj in typeList) {
        if (typeList[obj].value === type) {
            return typeList[obj].label;
        }
    }
    return '';
};

export default userCommon;
