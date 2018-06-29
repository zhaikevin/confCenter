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
                    <Input v-model="searchName" icon="search" @on-change="handleSearch" placeholder="请输入姓名搜索..." style="width: 200px" />
                </Row>
            </Card>
            <div class="edittable-table-height-con">
                <multi-page-table refs="userTable" v-model="tableData" :columns-list="columnsList" :total="total" 
                :page-size="pageSize" @reload="reload" :page-size-opts="[1,2]"
                @on-disable="disable" @on-enable="enable" @on-edit="showEditUser"></multi-page-table>
            </div>
            </Col>
        </Row>
        <Modal v-model="editUserModal" :closable='false' :mask-closable=false :width="400">
            <h3 slot="header" style="color:#2D8CF0">修改用户</h3>
            <Form ref="editUserForm" :model="editUserForm" :rules="rules" :label-width="100" label-position="right">
                <Input v-model="editUserForm.id" v-show="false" />
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
import multiPageTable from '../common/table/multi-page-table.vue';
import userCommon from './user_common.js';

export default {
    components: {
        multiPageTable
    },
    data() {
        return {
            searchName: '',
            columnsList: [],
            tableData: [],
            total: 0,
            pageSize: 1,
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
        getData(page, size) {
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
                success: function(data) {
                    that.tableData = data.rows;
                    that.total = data.total;
                },
                vm: that
            });
        },
        disable: function(val, index) {
            var that = this;
            var id = this.tableData[index].id;
            util.ajax({
                method: 'post',
                url: 'user/disable',
                data: {
                    'id': id
                },
                success: function(data) {
                    that.$Message.success('禁用成功');
                    that.reload(1,that.pageSize);
                },
                vm: that
            });
        },
        enable: function(val, index) {
            var that = this;
            var id = this.tableData[index].id;
            util.ajax({
                method: 'post',
                url: 'user/enable',
                data: {
                    'id': id
                },
                success: function(data) {
                    that.$Message.success('启用成功');
                    that.reload(1,that.pageSize);
                },
                vm: that
            });
        },
        showEditUser(val, index) {
            this.editUserModal = true;
            var that = this;
            var id = this.tableData[index].id;
            util.ajax({
                method: 'get',
                url: 'user/detail',
                params: {
                    'id': id
                },
                success: function(data) {
                    util.copyData(that.editUserForm, data);
                },
                vm: that
            });
        },
        cancelEditUser() {
            this.editUserModal = false;
            this.$refs.editUserForm.resetFields();
        },
        saveEditUser() {
            this.$refs.editUserForm.validate((valid) => {
                if (valid) {
                    this.saveUserLoading = true;
                    var data = JSON.parse(JSON.stringify(this.editUserForm));
                    var that = this;
                    util.ajax({
                        method: 'post',
                        url: 'user/modify',
                        data: data,
                        success: function(data) {
                            that.editUserModal = false;
                            that.reload(1,that.pageSize);
                        },
                        enable: function() {
                            that.saveUserLoading = false;
                        },
                        vm: that
                    });
                }
            });
        },
        handleSearch() {
            this.reload(1,this.pageSize);
        },
        reload(current,pageSize) {
            this.getData(current, pageSize);
        },
        init() {
            this.reload(1,this.pageSize);
            this.columnsList = userCommon.tableColumns;
            this.statusList = userCommon.statusList;
            this.typeList = userCommon.typeList;
            this.rules = userCommon.rules;
        }
    },
    created() {
        this.init();
    }
};
</script>
