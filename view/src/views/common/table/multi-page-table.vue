<style lang="less">
@import './multi-page-table.less';
</style>

<template>
    <div>
        <Table :ref="refs" :columns="columnsList" :data="thisTableData" highlight-row border disabled-hover></Table>
        <div v-if="multiPage" style="margin: 10px;overflow: hidden">
            <div style="float: right;">
                <Page :total="total" :current="current" :page-size="pageSize" :page-size-opts="pageSizeOpts" 
                show-elevator show-total show-sizer
                @on-change="changePage" @on-page-size-change="changeSize"></Page>
            </div>
        </div>
    </div>
</template>

<script>
const editButton = (vm, h, currentRow, index, item) => {
    return h('Button', {
        props: {
            type: item.buttonType ? item.buttonType : 'primary'
        },
        style: {
            margin: '0 5px'
        },
        on: {
            'click': () => {
                if (item.functions) {
                    item.functions.forEach(item => {
                        vm.$emit(item, vm.handleBackdata(vm.thisTableData), index);
                    });
                }
            }
        }
    }, item.name ? item.name : '编辑');
};
const deleteButton = (vm, h, currentRow, index, item) => {
    return h('Poptip', {
        props: {
            confirm: true,
            title: item.content ? item.content : '您确定要删除这条数据吗?',
            transfer: true
        },
        on: {
            'on-ok': () => {
                if (item.functions) {
                    item.functions.forEach(item => {
                        vm.$emit(item, vm.handleBackdata(vm.thisTableData), index);
                    });
                }
            }
        }
    }, [
            h('Button', {
                style: {
                    margin: '0 5px'
                },
                props: {
                    type: item.buttonType ? item.buttonType : 'error',
                    placement: 'top'
                }
            }, item.name ? item.name : '删除')
        ]);
};
export default {
    name: 'multiPageTable',
    props: {
        refs: String,
        columnsList: Array,
        total: Number,
        pageSize: Number,
        pageSizeOpts: {
            type: Array,
            default: () => [10, 20, 30, 40, 50]
        },
        multiPage:{
            type: Boolean,
            default:true
        },
        value: Array,
        editIncell: {
            type: Boolean,
            default: false
        },
        hoverShow: {
            type: Boolean,
            default: false
        }
    },
    data() {
        return {
            columns: [],
            thisTableData: [],
            current: 1
        };
    },
    created() {
        this.init();
    },
    methods: {
        init() {
            let vm = this;
            this.thisTableData = JSON.parse(JSON.stringify(this.value));;
            this.columnsList.forEach(item => {
                if (item.handle) {
                    item.render = (h, param) => {
                        let currentRowData = this.thisTableData[param.index];
                        let children = [];
                        item.handle.forEach(item => {
                            if (item.type === 'edit') {
                                children.push(editButton(this, h, currentRowData, param.index, item));
                            } else if (item.type === 'delete') {
                                children.push(deleteButton(this, h, currentRowData, param.index, item));
                            }
                        });
                        return h('div', children);
                    };
                }
            });
        },
        handleBackdata(data) {
            let clonedData = JSON.parse(JSON.stringify(data));
            return clonedData;
        },
        changePage: function(page) {
            this.$emit("reload", page);
        },
        changeSize: function(size) {
            this.$emit("reload", this.current, size,true);
        }
    },
    watch: {
        value(data) {
            this.init();
        }
    }
};
</script>
