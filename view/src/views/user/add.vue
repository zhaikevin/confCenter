<style lang="less">
@import '../../styles/common.less';
</style>

<template>
    <div>
        <Modal v-model="show" :closable='false' :mask-closable=false :width="400">
            <h3 slot="header" style="color:#2D8CF0">新建用户</h3>
            <Form ref="addUserForm" :model="addUserForm" :rules="rules" :label-width="100" label-position="right">
                <FormItem label="用户姓名" prop="userName">
                    <Input v-model="addUserForm.userName" style="width:200px"></Input>
                </FormItem>
                <FormItem label="邮箱" prop="mail">
                    <Input v-model="addUserForm.mail" style="width:200px"></Input>
                </FormItem>
                <FormItem label="状态" prop="status">
                    <Select v-model="addUserForm.status" style="width:200px">
                        <Option v-for="item in statusList" :value="item.value" :key="item.value">{{ item.label }}
                        </Option>
                    </Select>
                </FormItem>
                <FormItem label="账户类型" prop="type">
                    <Select v-model="addUserForm.type" style="width:200px">
                        <Option v-for="item in typeList" :value="item.value" :key="item.value">{{ item.label }}
                        </Option>
                    </Select>
                </FormItem>
            </Form>
            <div slot="footer">
                <Button type="text" @click="cancelAddUser">取消</Button>
                <Button type="primary" :loading="saveUserLoading" @click="saveAddUser">保存</Button>
            </div>
        </Modal>
    </div>
</template>

<script>
import util from '@/libs/util';
import userCommon from './user_common.js';
export default {
    name: 'addUserModal',
    props: {
        refs: String,
        value: Boolean
    },
    data() {
        return {
            addUserForm: {
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
        cancelAddUser() {
            this.$emit("close", false);
            this.$refs.addUserForm.resetFields();
        },
        saveAddUser() {
            this.$refs.addUserForm.validate((valid) => {
                if (valid) {
                    var data = JSON.parse(JSON.stringify(this.addUserForm));
                    var that = this;
                    util.ajax({
                        method: 'post',
                        url: 'user/create',
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
        }
    }
};
</script>
        