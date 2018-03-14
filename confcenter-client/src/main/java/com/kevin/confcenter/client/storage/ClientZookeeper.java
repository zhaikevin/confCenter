package com.kevin.confcenter.client.storage;

import com.kevin.confcenter.common.bean.vo.ClientDataSource;
import com.kevin.confcenter.common.consts.SourceTypeEnum;
import com.kevin.confcenter.common.utils.ConfCenterZookeeper;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.List;

/**
 * @Author: kevin
 * @Description: 客户端zk
 * @Date: Created In 2018/3/12 18:45
 */
public class ClientZookeeper {

    /**
     * ZK客户端
     */
    private ZkClient zkClient;

    /**
     * ZK
     */
    private ConfCenterZookeeper confCenterZookeeper;

    /**
     * storage
     */
    private DataStorageManager dataStorageManager;

    public ClientZookeeper(ZkClient zkClient, ConfCenterZookeeper confCenterZookeeper,
                           DataStorageManager dataStorageManager) {
        this.zkClient = zkClient;
        this.confCenterZookeeper = confCenterZookeeper;
        this.dataStorageManager = dataStorageManager;
    }

    /**
     * 从zk拉取数据
     */
    public void loadDataFromZk() {
        DataChangeZkListener normalDataListener = new DataChangeZkListener(SourceTypeEnum.NORMAL.getVal());
        DataChangeZkListener publicDataListener = new DataChangeZkListener(SourceTypeEnum.PUBLIC.getVal());
        this.zkClient.subscribeChildChanges(this.confCenterZookeeper.getNormalDataSourcePath(), normalDataListener);
        this.zkClient.subscribeChildChanges(this.confCenterZookeeper.getPublicDataSourcePath(), publicDataListener);
        normalDataListener.syncedUpdateData();
        publicDataListener.syncedUpdateData();
    }

    final class DataChangeZkListener implements IZkChildListener {

        /**
         * 数据源类型
         */
        private Integer sourceType;

        public Integer getSourceType() {
            return sourceType;
        }

        public DataChangeZkListener(Integer sourceType) {
            this.sourceType = sourceType;
        }

        @Override
        public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
            this.syncedUpdateData();
        }

        /**
         * 异步更新数据
         */
        void syncedUpdateData() {
            List<ClientDataSource> dataSourceList = ClientZookeeper.this.confCenterZookeeper.getAllDataSource(this.sourceType);
            ClientZookeeper.this.dataStorageManager.refresh(dataSourceList, this.sourceType);
        }
    }

}
