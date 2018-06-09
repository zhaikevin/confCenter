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
                        <Input v-model="searchName" icon="search" @on-change="handleSearch" placeholder="请输入姓名搜索..."
                               style="width: 200px"/>
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
        <Modal v-model="editUserModal" :closable='false' :mask-closable=false :width="400">
            <h3 slot="header" style="color:#2D8CF0">修改用户</h3>
            <Form ref="editUserForm" :model="editUserForm" :rules="rules" :label-width="100" label-position="right">
                <Input v-model="editUserForm.id" v-show="false"/>
                <FormItem label="用户姓名" prop="userName">
                    <Input v-model="editUserForm.userName" style="width:200px"></Input>
                </FormItem>
                <FormItem label="邮箱" prop="mail">
                    <Input v-model="editUserForm.mail" style="width:200px"></Input>
                </FormItem>
                <FormItem label="状态" prop="status">
                    <Select v-model="editUserForm.status" style="width:200px">
                        <Option v-for="item in statusList" :value="item.value" :key="item.value">{{ item.label }}
                        </Option>
                    </Select>
                </FormItem>
                <FormItem label="账户类型" prop="type">
                    <Select v-model="editUserForm.type" style="width:200px">
                        <Option v-for="item in typeList" :value="item.value" :key="item.value">{{ item.label }}
                        </Option>
                    </Select>
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
    import util from '@/libs/util';
    import canEditTable from '../common/canEditTable.vue';
    import userCommon from './user_common.js';

    export default {
        components: {
            canEditTable
        },
        data () {
            return {
                searchName: '',
                columnsList: [],
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
                },
                statusList: [],
                typeList: [],
                rules: {}
            };
        },
        methods: {
            getData (page, size) {
                var that = this;
                var params = {};
                if (this.searchName) {
                    params.userName = this.searchName;
                }
                util.ajax({
                    method: 'post',
                    url: 'user/list',
                    data: {
                        'page': page,
                        'rows': size,
                        'params': params
                    },
                    success: function (data) {
                        that.tableData = data.rows;
                        that.total = data.total;
                    },
                    vm: that
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
                util.ajax({
                    method: 'post',
                    url: 'user/disable',
                    data: {
                        'id': id
                    },
                    success: function (data) {
                        that.$Message.success('禁用成功');
                        that.reload();
                    },
                    vm: that
                });
            },
            enable: function (val, index) {
                var that = this;
                var id = this.tableData[index].id;
                util.ajax({
                    method: 'post',
                    url: 'user/enable',
                    data: {
                        'id': id
                    },
                    success: function (data) {
                        that.$Message.success('启用成功');
                        that.reload();
                    },
                    vm: that
                });
            },
            showEditUser (val, index) {
                this.editUserModal = true;
                var that = this;
                var id = this.tableData[index].id;
                util.ajax({
                    method: 'get',
                    url: 'user/detail',
                    params: {
                        'id': id
                    },
                    success: function (data) {
                        util.copyData(that.editUserForm, data);
                    },
                    vm: that
                });
            },
            cancelEditUser () {
                this.editUserModal = false;
                this.$refs.editUserForm.resetFields();
            },
            saveEditUser () {
                this.$refs.editUserForm.validate((valid) => {
                    if (valid) {
                        this.saveUserLoading = true;
                        var data = JSON.parse(JSON.stringify(this.editUserForm));
                        var that = this;
                        util.ajax({
                            method: 'post',
                            url: 'user/modify',
                            data: data,
                            success: function (data) {
                                that.editUserModal = false;
                                that.reload();
                            },
                            enable: function () {
                                that.saveUserLoading = false;
                            },
                            vm: that
                        });
                    }
                });
            },
            handleSearch () {
                this.reload();
            },
            reload () {
                this.getData(this.current, this.pageSize);
            },
            init () {
                this.reload();
                this.columnsList = userCommon.tableColumns;
                this.statusList = userCommon.statusList;
                this.typeList = userCommon.typeList;
                this.rules = userCommon.rules;
            }
        },
        created () {
            this.init();
        }
    };
</script>
