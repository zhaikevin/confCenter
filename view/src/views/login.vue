<style lang="less">
    @import './login.less';
</style>

<template>
    <div class="login" @keydown.enter="handleSubmit">
        <div class="login-con">
            <Card :bordered="false">
                <p slot="title">
                    <Icon type="log-in"></Icon>
                    欢迎登录
                </p>
                <div class="form-con">
                    <Form ref="loginForm" :model="form" :rules="rules">
                        <FormItem prop="userName">
                            <Input v-model="form.userName" placeholder="请输入用户名">
                            <span slot="prepend">
                                    <Icon :size="16" type="person"></Icon>
                                </span>
                            </Input>
                        </FormItem>
                        <FormItem prop="password">
                            <Input type="password" v-model="form.password" placeholder="请输入密码">
                            <span slot="prepend">
                                    <Icon :size="14" type="locked"></Icon>
                                </span>
                            </Input>
                        </FormItem>
                        <FormItem>
                            <Button @click="handleSubmit" type="primary" long>登录</Button>
                        </FormItem>
                    </Form>
                </div>
            </Card>
        </div>
    </div>
</template>

<script>
    import Cookies from 'js-cookie';
    import Util from '@/libs/util';

    export default {
        data () {
            return {
                form: {
                    userName: '',
                    password: ''
                },
                rules: {
                    userName: [
                        {required: true, message: '账号不能为空', trigger: 'blur'}
                    ],
                    password: [
                        {required: true, message: '密码不能为空', trigger: 'blur'}
                    ]
                }
            };
        },
        methods: {
            handleSubmit () {
                this.$refs.loginForm.validate((valid) => {
                    if (valid) {
                        var that = this;
                        Util.ajax({
                            method: 'post',
                            url: 'user/login',
                            data: {
                                'userName': this.form.userName,
                                'password': this.form.password
                            },
                            success: function (data) {
                                Cookies.set('id', data.id);
                                Cookies.set('user', data.userName);
                                Cookies.set('token', data.token);
                                Cookies.set('type', data.type);
                                that.$router.push({
                                    name: 'home_index'
                                });
                                that.$store.commit('setAvator', '/src/images/profile_pic.jpg');
                            },
                            vm: that
                        });
                    }
                });
            }
        }
    };
</script>

<style>

</style>
