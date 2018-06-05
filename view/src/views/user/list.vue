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
                                    :columns-list="columnsList" @on-disable="disable"
                                    @on-enable="enable" @on-edit="showEditUser"></can-edit-table>
                    <div style="margin: 10px;overflow: hidden">
                        <div style="float: right;">
                            <Page :total="total" :current="current" :page-size="pageSize" show-elevator show-total
                                  show-sizer @on-change="changePage" @on-page-size-change="changeSize"></Page>
                        </div>
                    </div>
                </div>
            </Col>
        </Row>
        <Modal v-model="editUserModal" :closable='false' :mask-closable=false :width="500">
            <h3 slot="header" style="color:#2D8CF0">修改用户</h3>
            <Form ref="editUserForm" :model="editUserForm" :label-width="100" label-position="right">
                <Input v-model="editUserForm.id" v-show="false"/>
                <FormItem label="用户姓名" prop="userName">
                    <Input v-model="editUserForm.userName"></Input>
                </FormItem>
                <FormItem label="邮箱" prop="mail">
                    <Input v-model="editUserForm.mail"></Input>
                </FormItem>
                <FormItem label="状态" prop="status">
                    <Input v-model="editUserForm.status"></Input>
                </FormItem>
                <FormItem label="账户类型" prop="type">
                    <Input v-model="editUserForm.type"></Input>
                </FormItem>
            </Form>
            <div slot="footer">
                <Button type="text" @click="cancelEditUser">取消</Button>
                <Button type="primary" :loading="saveUserLoading" @click="saveEditUser">保存</Button>
            </div>
        </Modal>
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
                        key: 'type',
                        align: 'center'
                    },
                    {
                        title: '状态',
                        key: 'status',
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
                ],
                tableData: [],
                total: 0,
                current: 1,
                pageSize: 10,
                editUserModal: false,
                saveUserLoading: false,
                editUserForm: {
                    id: '',
                    userName: '',
                    mail: '',
                    status: '',
                    type: ''
                }
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
            disable: function (val, index) {
                var that = this;
                var id = this.tableData[index].id;
                Util.ajax({
                    method: 'post',
                    url: 'user/disable',
                    data: {
                        'id': id
                    }
                }).then(function (res) {
                    if (res.data.status === 0) {
                        that.$Message.success('禁用成功');
                        that.getData(that.current, that.pageSize);
                    } else {
                        that.$Message.error(res.data.statusInfo);
                    }
                }).catch(function (err) {
                    alert(err);
                });
            },
            enable: function (val, index) {
                var that = this;
                var id = this.tableData[index].id;
                Util.ajax({
                    method: 'post',
                    url: 'user/enable',
                    data: {
                        'id': id
                    }
                }).then(function (res) {
                    if (res.data.status === 0) {
                        that.$Message.success('启用成功');
                        that.getData(that.current, that.pageSize);
                    } else {
                        that.$Message.error(res.data.statusInfo);
                    }
                }).catch(function (err) {
                    alert(err);
                });
            },
            showEditUser (val, index) {
                this.editUserModal = true;
                var that = this;
                var id = this.tableData[index].id;
                Util.ajax({
                    method: 'get',
                    url: 'user/detail',
                    params: {
                        'id': id
                    }
                }).then(function (res) {
                    if (res.data.status === 0) {
                        that.editUserForm.id = res.data.data.id;
                        that.editUserForm.userName = res.data.data.userName;
                        that.editUserForm.mail = res.data.data.mail;
                        that.editUserForm.status = res.data.data.status;
                        that.editUserForm.type = res.data.data.type;
                    } else {
                        alert(res.data.statusInfo);
                    }
                }).catch(function (err) {
                    alert(err);
                });
            },
            cancelEditUser () {
                this.editUserModal = false;
            },
            saveEditUser () {
                this.saveUserLoading = true;
                var data = JSON.parse(JSON.stringify(this.editUserForm));
                var that = this;
                Util.ajax({
                    method: 'post',
                    url: 'user/modify',
                    data: data
                }).then(function (res) {
                    if (res.data.status === 0) {
                        that.saveUserLoading = false;
                        that.editUserModal = false;
                        that.getData(that.current, that.pageSize);
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
