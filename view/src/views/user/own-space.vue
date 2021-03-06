<style lang="less">
    @import 'own-space.less';
</style>

<template>
    <div>
        <Card>
            <p slot="title">
                <Icon type="person"></Icon>
                个人信息
            </p>
            <div>
                <Form ref="userForm" :model="userForm" :rules="infoValidate" :label-width="100" label-position="right">
                    <FormItem label="用户姓名：">
                        <span>{{ userForm.userName }}</span>
                    </FormItem>
                    <FormItem label="邮箱：" prop="mail">
                        <div style="display:inline-block;width:150px;">
                            <Input v-model="userForm.mail"></Input>
                        </div>
                    </FormItem>
                    <FormItem label="状态：">
                        <span>{{ userForm.status }}</span>
                    </FormItem>
                    <FormItem label="账户类型：">
                        <span>{{ userForm.type }}</span>
                    </FormItem>
                    <FormItem label="登录密码：">
                        <Button type="text" size="small" @click="showEditPassword">修改密码</Button>
                    </FormItem>
                    <div>
                        <Button type="primary" style="margin-left:75px" :loading="saveUserLoading" @click="saveEditUser">保存</Button>
                    </div>
                </Form>
            </div>
        </Card>
        <Modal v-model="editPasswordModal" :closable='false' :mask-closable=false :width="500">
            <h3 slot="header" style="color:#2D8CF0">修改密码</h3>
            <Form ref="editPasswordForm" :model="editPasswordForm" :label-width="100" label-position="right"
                  :rules="passwordValidate">
                <FormItem label="原密码" prop="oldPass" :error="oldPassError">
                    <Input v-model="editPasswordForm.oldPass" placeholder="请输入现在使用的密码"></Input>
                </FormItem>
                <FormItem label="新密码" prop="newPass">
                    <Input v-model="editPasswordForm.newPass" placeholder="请输入新密码，至少6位字符"></Input>
                </FormItem>
                <FormItem label="确认新密码" prop="rePass">
                    <Input v-model="editPasswordForm.rePass" placeholder="请再次输入新密码"></Input>
                </FormItem>
            </Form>
            <div slot="footer">
                <Button type="text" @click="cancelEditPass">取消</Button>
                <Button type="primary" :loading="savePassLoading" @click="saveEditPass">保存</Button>
            </div>
        </Modal>
    </div>
</template>

<script>
    import Cookies from 'js-cookie';
    import util from '@/libs/util';
    import userCommon from './user_common.js';

    export default {
        name: 'ownspace_index',
        data () {
            const valideRePassword = (rule, value, callback) => {
                if (value !== this.editPasswordForm.newPass) {
                    callback(new Error('两次输入密码不一致'));
                } else {
                    callback();
                }
            };
            return {
                userForm: {
                    id: '',
                    userName: '',
                    mail: '',
                    status: '',
                    type: ''
                },
                infoValidate: {},
                saveUserLoading: false,
                identifyError: '', // 验证码错误
                editPasswordModal: false, // 修改密码模态框显示
                savePassLoading: false,
                oldPassError: '',
                editPasswordForm: {
                    oldPass: '',
                    newPass: '',
                    rePass: ''
                },
                passwordValidate: {
                    oldPass: [
                        {required: true, message: '请输入原密码', trigger: 'blur'}
                    ],
                    newPass: [
                        {required: true, message: '请输入新密码', trigger: 'blur'},
                        {min: 6, message: '请至少输入6个字符', trigger: 'blur'},
                        {max: 32, message: '最多输入32个字符', trigger: 'blur'}
                    ],
                    rePass: [
                        {required: true, message: '请再次输入新密码', trigger: 'blur'},
                        {validator: valideRePassword, trigger: 'blur'}
                    ]
                }
            };
        },
        methods: {
            showEditPassword () {
                this.editPasswordModal = true;
            },
            cancelEditPass () {
                this.editPasswordModal = false;
            },
            saveEditPass () {
                this.$refs['editPasswordForm'].validate((valid) => {
                    if (valid) {
                        this.savePassLoading = true;
                        // you can write ajax request here
                    }
                });
            },
            saveEditUser () {
                this.$refs.userForm.validate((valid) => {
                    if (valid) {
                        this.saveUserLoading = true;
                        var that = this;
                        util.ajax({
                            method: 'post',
                            url: 'user/modify',
                            data: {
                                'id': that.userForm.id,
                                'mail': that.userForm.mail
                            },
                            success: function (data) {
                                that.$Message.success('修改成功');
                                that.getInfo();
                            },
                            enable: function () {
                                that.saveUserLoading = false;
                            },
                            vm: that
                        });
                    }
                });
            },
            getInfo () {
                this.userForm.id = Cookies.get('id');
                var that = this;
                util.ajax({
                    method: 'get',
                    url: 'user/detail',
                    params: {
                        'id': that.userForm.id
                    },
                    success: function (data) {
                        that.userForm.userName = data.userName;
                        that.userForm.mail = data.mail;
                        that.userForm.status = userCommon.formatStatus(data.status);
                        that.userForm.type = userCommon.formatType(data.type);
                    },
                    vm: that
                });
            },
            init () {
                this.getInfo();
                this.infoValidate = userCommon.rules;
            }
        },
        created () {
            this.init();
        }
    };
</script>
