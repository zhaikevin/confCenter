package com.kevin.confcenter.client.storage;

import com.kevin.confcenter.common.utils.ConfCenterZookeeper;
import org.I0Itec.zkclient.ZkClient;

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

    public ZkClient getZkClient() {
        return zkClient;
    }

    public ConfCenterZookeeper getConfCenterZookeeper() {
        return confCenterZookeeper;
    }

    public DataStorageManager getDataStorageManager() {
        return dataStorageManager;
    }

}
