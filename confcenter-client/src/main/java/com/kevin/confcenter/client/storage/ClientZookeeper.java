package com.kevin.confcenter.client.storage;

import com.kevin.confcenter.common.bean.vo.ClientDataSource;
import com.kevin.confcenter.common.consts.SourceTypeEnum;
import com.kevin.confcenter.common.utils.ConfCenterZookeeper;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.commons.collections.CollectionUtils;

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
        subscribeChanges(SourceTypeEnum.NORMAL);
        subscribeChanges(SourceTypeEnum.PUBLIC);
        getAllData(SourceTypeEnum.NORMAL);
        getAllData(SourceTypeEnum.PUBLIC);
    }

    /**
     * 监听变化
     *
     * @param sourceType
     */
    private void subscribeChanges(SourceTypeEnum sourceType) {
        DataChangeZkListener listener = new DataChangeZkListener(sourceType);
        String sourcePath = this.confCenterZookeeper.getPublicDataSourcePath();
        if (sourceType.equals(SourceTypeEnum.NORMAL)) {
            sourcePath = this.confCenterZookeeper.getNormalDataSourcePath();
        }
        this.zkClient.subscribeChildChanges(sourcePath, listener);
        List<String> sourceKeys = this.confCenterZookeeper.getAllKeys(sourceType);
        if (CollectionUtils.isNotEmpty(sourceKeys)) {
            for (String sourceKey : sourceKeys) {
                String dataPath = sourcePath + "/" + sourceKey;
                this.zkClient.subscribeDataChanges(dataPath, listener);
            }
        }
    }

    /**
     * 获取全部数据
     *
     * @param sourceType
     */
    private void getAllData(SourceTypeEnum sourceType) {
        List<ClientDataSource> dataSourceList = ClientZookeeper.this.confCenterZookeeper.getAllDataSource(sourceType);
        this.dataStorageManager.syncAll(dataSourceList, sourceType);
    }

    final class DataChangeZkListener implements IZkChildListener, IZkDataListener {

        private SourceTypeEnum sourceType;

        public DataChangeZkListener(SourceTypeEnum sourceType) {
            this.sourceType = sourceType;
        }

        @Override
        public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
            ClientZookeeper.this.dataStorageManager.addOrDeleteSource(sourceType, currentChilds);
        }

        @Override
        public void handleDataChange(String dataPath, Object data) throws Exception {
            //处理结点数据变化
            int index = dataPath.lastIndexOf("/");
            String sourceKey = dataPath.substring(index + 1);
            ClientDataSource dataSource = new ClientDataSource();
            dataSource.setSourceKey(sourceKey);
            dataSource.setSourceValue(data.toString());
            dataSource.setSourceType(sourceType);
            ClientZookeeper.this.dataStorageManager.updateSourceByKey(dataSource);
        }

        @Override
        public void handleDataDeleted(String dataPath) throws Exception {

        }

    }

}
