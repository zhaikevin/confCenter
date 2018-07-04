import util from '@/libs/util';

let menuCommon = {};

menuCommon.accessList = [
    {
        value: 0,
        label: '普通权限'
    },
    {
        value: 1,
        label: '管理员权限'
    }
];

menuCommon.iconList = ['ios-grid-view', 'arrow-move', 'android-sad', 'ios-infinite'];

menuCommon.getParentNodeList = function (vm) {
    let parentNodeList = [{ value: 0, label: '根节点' }];
    util.ajax({
        method: 'get',
        url: 'menu/parentNodes',
        success: function (data) {
            if (data) {
                data.forEach(item => {
                    let val = {};
                    val.value = item.id;
                    val.label = item.title;
                    parentNodeList.push(val);
                });
            }
            vm.parentNodeList = parentNodeList;
        },
        vm: vm
    });
};

menuCommon.rules = {
    parentId: [
        {required: true, message: '父节点不能为空', pattern: /.+/, trigger: 'change'}
    ],
    path: [
        {required: true, message: '路径不能为空', trigger: 'blur'},
        {type: 'string', max: 255, message: '路径不能超过255个字符', trigger: 'blur'}
    ],
    icon: [
        {required: true, message: 'icon不能为空', pattern: /.+/, trigger: 'change'}
    ],
    name: [
        {required: true, message: '名称不能为空', trigger: 'blur'},
        {type: 'string', max: 255, message: '名称不能超过255个字符', trigger: 'blur'}
    ],
    title: [
        {required: true, message: '标题不能为空', trigger: 'blur'},
        {type: 'string', max: 255, message: '标题不能超过255个字符', trigger: 'blur'}
    ],
    component: [
        {required: true, message: '组件不能为空', trigger: 'blur'},
        {type: 'string', max: 255, message: '组件不能超过255个字符', trigger: 'blur'}
    ],
    access: [
        {required: true, message: '权限不能为空', pattern: /.+/, trigger: 'change'}
    ],
    remark: [
        {type: 'string', max: 255, message: '备注不能超过255个字符', trigger: 'blur'}
    ]
};

export default menuCommon;
