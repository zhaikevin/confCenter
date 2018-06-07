import Util from '@/libs/util';

export const tableColumns = [
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
            return h('div', this.formatType(params.row.type));
        }
    },
    {
        title: '状态',
        key: 'status',
        align: 'center',
        render: (h, params) => {
            return h('div', this.formatStatus(params.row.status));
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
