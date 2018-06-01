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
                    <Table border highlight-row :columns="columns" :data="data"></Table>
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

    export default {
        data () {
            return {
                searchConName1: '',
                columns: [
                    {
                        width: 60,
                        align: 'center',
                        type: 'index'
                    },
                    {
                        title: '用户名称',
                        key: 'userName'
                    },
                    {
                        title: '账户类型',
                        key: 'typeName'
                    },
                    {
                        title: '状态',
                        key: 'statusName'
                    },
                    {
                        title: '邮箱',
                        key: 'mail'
                    },
                    {
                        title: '新建时间',
                        key: 'createTime',
                        render: (h, params) => {
                            return h('div', Util.formatDate(params.row.createTime));
                        }
                    }
                ],
                data: [],
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
                        that.data = res.data.data.rows;
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
            }
        },
        mounted () {
            this.getData(this.current, this.pageSize);
        }
    };
</script>
