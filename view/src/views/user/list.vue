<style lang="less">
    @import '../../styles/common.less';
    @import '../../styles/table.less';
</style>

<template>
    <div>
        <Row :gutter="10">
            <Col>
                <Card>
                    <Row>
                        <Input v-model="searchConName1" icon="search" @on-change="search"
                               placeholder="请输入姓名搜索..." style="width: 200px"/>
                    </Row>
                </Card>
                <div class="edittable-table-height-con">
                    <can-edit-table border highlight-row refs="userTable" v-model="tableData"
                                    :columns-list="columnsList" @on-forbidden="forbidden"></can-edit-table>
                    <div style="margin: 10px;overflow: hidden">
                        <div style="float: right;">
                            <Page :total="total" :current="current" :page-size="pageSize" show-elevator show-total
                                  show-sizer @on-change="changePage" @on-page-size-change="changeSize"></Page>
                        </div>
                    </div>
                </div>
            </Col>
        </Row>
    </div>
</template>

<script>
    import Util from '@/libs/util';
    import canEditTable from '../common/canEditTable.vue';

    export default {
        components: {
            canEditTable
        },
        data () {
            return {
                searchConName1: '',
                columnsList: [
                    {
                        title: '序号',
                        width: 80,
                        align: 'center',
                        type: 'index'
                    },
                    {
                        title: '用户名称',
                        key: 'userName',
                        align: 'center'
                    },
                    {
                        title: '账户类型',
                        key: 'typeName',
                        align: 'center'
                    },
                    {
                        title: '状态',
                        key: 'statusName',
                        align: 'center'
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
                        handle: [{type: 'delete', name: '禁用', content: '您确定要禁用该用户吗?', functions: ['on-forbidden']},
                            {type: 'delete', name: '启用', content: '您确定要启用该用户吗?', buttonType: 'info'}]
                    }
                ],
                tableData: [],
                total: 0,
                current: 1,
                pageSize: 10
            };
        },
        methods: {
            search (data, argumentObj) {
                let res = data;
                let dataClone = data;
                for (let argu in argumentObj) {
                    if (argumentObj[argu].length > 0) {
                        res = dataClone.filter(d => {
                            return d[argu].indexOf(argumentObj[argu]) > -1;
                        });
                        dataClone = res;
                    }
                }
                return res;
            },
            getData (page, size) {
                var that = this;
                var params = {};
                params.page = page;
                params.rows = size;
                Util.ajax({
                    method: 'post',
                    url: 'user/list',
                    data: {
                        'page': page,
                        'rows': size
                    }
                }).then(function (res) {
                    if (res.data.status === 0) {
                        that.tableData = res.data.data.rows;
                        that.total = res.data.data.total;
                    } else {
                        alert(res.data.statusInfo);
                    }
                }).catch(function (err) {
                    alert(err);
                });
            },
            changePage: function (page) {
                this.getData(page, this.pageSize);
            },
            changeSize: function (size) {
                this.getData(this.current, size);
            },
            forbidden: function (val, index) {
                var id = this.tableData[index].id;
                Util.ajax({
                    method: 'post',
                    url: 'user/forbidden',
                    data: {
                        'id': id
                    }
                }).then(function (res) {
                    if (res.data.status === 0) {
                        this.$Message.success('禁用成功');
                    } else {
                        alert(res.data.statusInfo);
                    }
                }).catch(function (err) {
                    alert(err);
                });
            }
        },
        created () {
            this.getData(this.current, this.pageSize);
        }
    };
</script>
