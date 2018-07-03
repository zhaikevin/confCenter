<style lang="less">
@import '../../styles/common.less';
</style>

<template>
    <div>
        <Modal v-model="show" :closable='false' :mask-closable=false :width="400">
            <h3 slot="header" style="color:#2D8CF0">新建菜单</h3>
            <Form ref="addMenuForm" :model="addMenuForm" :rules="rules" :label-width="100" label-position="right">
                <FormItem label="父节点" prop="parentId">
                    <Select v-model="addMenuForm.parentId" style="width:200px">
                        <Option v-for="item in parentNodeList" :value="item.value" :key="item.value">{{ item.label }}
                        </Option>
                    </Select>
                </FormItem>
                <FormItem label="路径" prop="path">
                    <Input v-model="addMenuForm.path" style="width:200px"></Input>
                </FormItem>
                <FormItem label="icon" prop="icon">
                    <Select v-model="addMenuForm.icon" style="width:200px">
                       <Option v-for="item in iconList" :value="item" :key="item">{{ item }}
                        </Option>
                    </Select>
                </FormItem>
                <FormItem label="名称" prop="name">
                    <Input v-model="addMenuForm.name" style="width:200px"></Input>
                </FormItem>
                <FormItem label="标题" prop="title">
                    <Input v-model="addMenuForm.title" style="width:200px"></Input>
                </FormItem>
                <FormItem label="组件" prop="component">
                    <Input v-model="addMenuForm.component" style="width:200px"></Input>
                </FormItem>
                <FormItem label="权限" prop="access">
                    <Select v-model="addMenuForm.access" style="width:200px">
                        <Option v-for="item in accessList" :value="item.value" :key="item.value">{{ item.label }}
                        </Option>
                    </Select>
                </FormItem>
                <FormItem label="备注" prop="remark">
                    <Input v-model="addMenuForm.remark" style="width:200px"></Input>
                </FormItem>
            </Form>
            <div slot="footer">
                <Button type="text" @click="cancelAddMenu">取消</Button>
                <Button type="primary" :loading="saveMenuLoading" @click="saveAddMenu">保存</Button>
            </div>
        </Modal>
    </div>
</template>

<script>
import util from '@/libs/util';
import menuCommon from './menu_common.js';
export default {
    name: 'addMenuModal',
    props: {
        refs: String,
        value: Boolean
    },
    data() {
        return {
            addMenuForm: {
                parentId: '',
                path: '',
                icon: '',
                name: '',
                title: '',
                component: '',
                access: '',
                remark: ''
            },
            parentNodeList: [],
            iconList: [],
            accessList: [],
            rules: {},
            saveMenuLoading: false,
            show: false
        }
    },
    methods: {
        cancelAddMenu() {
            this.$emit("close", false);
            this.$refs.addMenuForm.resetFields();
        },
        saveAddMenu() {
            this.$refs.addMenuForm.validate((valid) => {
                if (valid) {
                    var data = JSON.parse(JSON.stringify(this.addMenuForm));
                    var that = this;
                    util.ajax({
                        method: 'post',
                        url: 'menu/create',
                        data: data,
                        success: function(data) {
                            that.$emit("close", true);
                        },
                        enable: function() {
                            that.saveMenuLoading = false;
                        },
                        vm: that
                    });
                }
            });
        },
        init() {
            this.accessList = menuCommon.accessList;
            this.iconList = menuCommon.iconList;
            this.rules = menuCommon.rules;
        }
    },
    created() {
        this.init();
    },
    watch: {
        value(data) {
            this.show = data;
            if(data) {
                this.parentNodeList = menuCommon.getParentNodeList(this);
            }
        }
    }
};
</script>
        