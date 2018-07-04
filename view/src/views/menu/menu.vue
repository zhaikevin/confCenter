<style lang="less">
@import '../../styles/common.less';
</style>

<template>
    <div>
        <Card>
            <Row>
                <Button @click="showAddMenuModal = true" type="primary">新建</Button>
                <Button type="info">修改</Button>
                <Button type="error">删除</Button>
            </Row>
            <Tree class="margin-top-20" :data="data" @on-select-change="onSelectChange">
            </Tree>
        </Card>
        <add-menu-modal refs="addMenu" v-model="showAddMenuModal" @close="closeAddMenuModal"></add-menu-modal>
    </div>
</template>

<script>
import util from '@/libs/util';
import addMenuModal from './add.vue';
export default {
    components: {
        addMenuModal
    },
    data() {
        return {
            data: [],
            selectedNode: {},
            showAddMenuModal: false
        };
    },
    methods: {
        getData() {
            var that = this;
            util.ajax({
                method: 'get',
                url: 'menu/list',
                success: function(data) {
                    that.data = data;
                },
                vm: that
            });
        },
        onSelectChange(item) {
            if (item && item instanceof Array && item.length > 0) {
                this.selectedNode = item[0].id;
            }
        },
        closeAddMenuModal(isReload) {
            this.showAddMenuModal = false;
            if (isReload) {
                this.getData();
                this.selectedNode = {};
            }
        },
        init() {
            this.getData();
        }
    },
    created() {
        this.init();
    }
};
</script>