<style lang="less">
@import '../../styles/common.less';
</style>

<template>
    <div>
        <Modal v-model="show" :closable='false' :mask-closable=false :width="400">
            <h3 slot="header" style="color:#2D8CF0">修改用户</h3>
            <Form ref="editUserForm" :model="editUserForm" :rules="rules" :label-width="100" label-position="right">
                <FormItem label="用户姓名" prop="userName">
                    <Input v-model="editUserForm.userName" style="width:200px" disabled="disabled"></Input>
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
import userCommon from './user_common.js';
export default {
    name: 'editUserModal',
    props: {
        refs: String,
        value: Boolean,
        editId: Number
    },
    data() {
        return {
            editUserForm: {
                id: '',
                userName: '',
                mail: '',
                status: '',
                type: ''
            },
            statusList: [],
            typeList: [],
            rules: {},
            saveUserLoading: false,
            show: false
        }
    },
    methods: {
        getUserInfo() {
            var that = this;
            var id = this.editId;
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
            this.$emit("close", false);
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
                            that.$emit("close", true);
                        },
                        enable: function() {
                            that.saveUserLoading = false;
                        },
                        vm: that
                    });
                }
            });
        },
        init() {
            this.statusList = userCommon.statusList;
            this.typeList = userCommon.typeList;
            this.rules = userCommon.rules;
        }
    },
    created() {
        this.init();
    },
    watch: {
        value(data) {
            this.show = data;
            if(data) {
                this.getUserInfo();
            }
        }
    }
};
</script>