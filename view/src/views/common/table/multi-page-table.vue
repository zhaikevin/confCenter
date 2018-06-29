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
            type: 'primary'
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
    }, '编辑');
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
const incellEditBtn = (vm, h, param) => {
    if (vm.hoverShow) {
        return h('div', {
            'class': {
                'show-edit-btn': vm.hoverShow
            }
        }, [
                h('Button', {
                    props: {
                        type: 'text',
                        icon: 'edit'
                    },
                    on: {
                        click: (event) => {
                            vm.edittingStore[param.index].edittingCell[param.column.key] = true;
                            vm.thisTableData = JSON.parse(JSON.stringify(vm.edittingStore));
                        }
                    }
                })
            ]);
    } else {
        return h('Button', {
            props: {
                type: 'text',
                icon: 'edit'
            },
            on: {
                click: (event) => {
                    vm.edittingStore[param.index].edittingCell[param.column.key] = true;
                    vm.thisTableData = JSON.parse(JSON.stringify(vm.edittingStore));
                }
            }
        });
    }
};
const saveIncellEditBtn = (vm, h, param) => {
    return h('Button', {
        props: {
            type: 'text',
            icon: 'checkmark'
        },
        on: {
            click: (event) => {
                vm.edittingStore[param.index].edittingCell[param.column.key] = false;
                vm.thisTableData = JSON.parse(JSON.stringify(vm.edittingStore));
                vm.$emit('input', vm.handleBackdata(vm.thisTableData));
                vm.$emit('on-cell-change', vm.handleBackdata(vm.thisTableData), param.index, param.column.key);
            }
        }
    });
};
const cellInput = (vm, h, param, item) => {
    return h('Input', {
        props: {
            type: 'text',
            value: vm.edittingStore[param.index][item.key]
        },
        on: {
            'on-change'(event) {
                let key = item.key;
                vm.edittingStore[param.index][key] = event.target.value;
            }
        }
    });
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
        url: String,
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
            edittingStore: [],
            current: 1
        };
    },
    created() {
        this.init();
    },
    methods: {
        init() {
            let vm = this;
            let editableCell = this.columnsList.filter(item => {
                if (item.editable) {
                    if (item.editable === true) {
                        return item;
                    }
                }
            });
            let cloneData = JSON.parse(JSON.stringify(this.value));
            let res = [];
            res = cloneData.map((item, index) => {
                let isEditting = false;
                if (this.thisTableData[index]) {
                    if (this.thisTableData[index].editting) {
                        isEditting = true;
                    } else {
                        for (const cell in this.thisTableData[index].edittingCell) {
                            if (this.thisTableData[index].edittingCell[cell] === true) {
                                isEditting = true;
                            }
                        }
                    }
                }
                if (isEditting) {
                    return this.thisTableData[index];
                } else {
                    this.$set(item, 'editting', false);
                    let edittingCell = {};
                    editableCell.forEach(item => {
                        edittingCell[item.key] = false;
                    });
                    this.$set(item, 'edittingCell', edittingCell);
                    return item;
                }
            });
            this.thisTableData = res;
            this.edittingStore = JSON.parse(JSON.stringify(this.thisTableData));
            this.columnsList.forEach(item => {
                if (item.editable) {
                    item.render = (h, param) => {
                        let currentRow = this.thisTableData[param.index];
                        if (!currentRow.editting) {
                            if (this.editIncell) {
                                return h('Row', {
                                    props: {
                                        type: 'flex',
                                        align: 'middle',
                                        justify: 'center'
                                    }
                                }, [
                                        h('Col', {
                                            props: {
                                                span: '22'
                                            }
                                        }, [
                                                !currentRow.edittingCell[param.column.key] ? h('span', currentRow[item.key]) : cellInput(this, h, param, item)
                                            ]),
                                        h('Col', {
                                            props: {
                                                span: '2'
                                            }
                                        }, [
                                                currentRow.edittingCell[param.column.key] ? saveIncellEditBtn(this, h, param) : incellEditBtn(this, h, param)
                                            ])
                                    ]);
                            } else {
                                return h('span', currentRow[item.key]);
                            }
                        } else {
                            return h('Input', {
                                props: {
                                    type: 'text',
                                    value: currentRow[item.key]
                                },
                                on: {
                                    'on-change'(event) {
                                        let key = param.column.key;
                                        vm.edittingStore[param.index][key] = event.target.value;
                                    }
                                }
                            });
                        }
                    };
                }
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
            clonedData.forEach(item => {
                delete item.editting;
                delete item.edittingCell;
                delete item.saving;
            });
            return clonedData;
        },
        changePage: function(page) {
            this.$emit("reload", page, this.pageSize);
        },
        changeSize: function(size) {
            this.$emit("reload", this.current, size);
        }
    },
    watch: {
        value(data) {
            this.init();
        }
    }
};
</script>
