<style lang="less">
@import '../../styles/common.less';
</style>

<template>
    <div>
        <Card>
        <Row>
            <Button  type="primary">新建</Button>
            <Button  type="info">修改</Button>
            <Button  type="error">删除</Button>
        </Row>
        <Tree class="margin-top-20" :data="data" @on-select-change="onSelectChange">
        </Tree>
        </Card>
    </div>
</template>

<script>
import util from '@/libs/util';
export default {
    data() {
        return {
            data: [],
            selectedNode: {}
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
            if(item && item instanceof Array && item.length > 0) {
                this.selectedNode = item[0].id;
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