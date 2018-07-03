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
    let parentNodeList = [{value:0,label:'根节点'}];
    util.ajax({
        method: 'get',
        url: 'menu/parentNode',
        success: function (data) {
            if(data) {
                for(let item in data) {
                    let val = {};
                    val.value = item.id;
                    val.lable = item.title;
                    parentNodeList.push(val);
                }
            }
            return parentNodeList;
        },
        vm: vm
    });
},

menuCommon.rules = [];