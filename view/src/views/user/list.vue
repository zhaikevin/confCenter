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
                    <Button @click="showAddUserModal = true" type="primary">新建</Button>
                    <Input v-model="searchName" icon="search" @on-change="handleSearch" placeholder="请输入姓名搜索..." style="width: 200px" />
                </Row>
            </Card>
            <div class="edittable-table-height-con">
                <common-table refs="userTable" v-model="tableData" :columns-list="columnsList" @on-disable="disable" @on-enable="enable" @on-edit="showEditUser"></common-table>
                <div style="margin: 10px;overflow: hidden">
                    <div style="float: right;">
                        <Page :total="total" :current="current" :page-size="pageSize" show-elevator show-total show-sizer @on-change="changePage" @on-page-size-change="changeSize"></Page>
                    </div>
                </div>
            </div>
            </Col>
        </Row>
        <add-user-modal refs="addUser" v-model="showAddUserModal" @close="closeAddUserModal"></add-user-modal>
        <edit-user-modal refs="editUser" v-model="showEditUserModal" @close="closeEditUserModal" :edit-id="editId"></edit-user-modal>
    </div>
</template>

<script>
import util from '@/libs/util';
import commonTable from '../common/table/common-table.vue';
import userCommon from './user_common.js';
import addUserModal from './add.vue';
import editUserModal from './edit.vue'

export default {
    components: {
        commonTable,
        addUserModal,
        editUserModal
    },
    data() {
        return {
            searchName: '',
            columnsList: [],
            tableData: [],
            total: 0,
            pageSize: 10,
            current: 1,
            showAddUserModal: false,
            showEditUserModal:false,
            editId: 0
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
                    that.reload(1, that.pageSize);
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
                    that.reload(1, that.pageSize);
                },
                vm: that
            });
        },
        closeAddUserModal(isReload) {
            this.showAddUserModal = false;
            if(isReload) {
                this.reload(1,this.pageSize);
            }
        },
        closeEditUserModal(isReload) {
            this.showEditUserModal = false;
            if(isReload) {
                this.reload(1,this.pageSize);
            }
        },
        showEditUser(val, index) {
            this.showEditUserModal = true;
            this.editId = this.tableData[index].id;
        },
        handleSearch() {
            this.reload(1, this.pageSize);
        },
        reload(current, pageSize) {
            this.current = current;
            this.pageSize = pageSize;
            this.getData(current, pageSize);
        },
        changePage: function(page) {
            this.reload(page, this.pageSize);
        },
        changeSize: function(size) {
            this.reload(this.page, size);
        },
        init() {
            this.reload(this.current, this.pageSize);
            this.columnsList = userCommon.tableColumns;
        }
    },
    created() {
        this.init();
    }
};
</script>
