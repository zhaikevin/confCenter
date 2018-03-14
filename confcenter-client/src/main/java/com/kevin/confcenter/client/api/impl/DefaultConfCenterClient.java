package com.kevin.confcenter.client.api.impl;

import com.kevin.confcenter.client.api.ConfCenterClient;
import com.kevin.confcenter.client.storage.ClientZookeeper;
import com.kevin.confcenter.client.storage.DataStorageManager;
import com.kevin.confcenter.client.storage.DefaultDataStorageManager;
import com.kevin.confcenter.common.bean.vo.ClientDataSource;
import com.kevin.confcenter.common.bean.vo.ConfCenterClientConf;
import com.kevin.confcenter.common.bean.vo.ZKConfig;
import com.kevin.confcenter.common.exception.IllegalParameterException;
import com.kevin.confcenter.common.utils.ConfCenterZookeeper;
import com.kevin.confcenter.common.utils.ZkUtil;
import org.I0Itec.zkclient.ZkClient;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 * @Author: kevin
 * @Description: 客户端默认实现
 * @Date: Created In 2018/3/6 9:33
 */
public class DefaultConfCenterClient implements ConfCenterClient {

    /**
     * 客户端配置
     */
    private ConfCenterClientConf clientConf;

    /**
     * ZK配置
     */
    private ZKConfig zkConfig;

    /**
     * ZK客户端
     */
    private ZkClient zkClient;

    /**
     * ZK
     */
    private ConfCenterZookeeper confCenterZookeeper;

    /**
     * 存储
     */
    private DataStorageManager dataStorageManager;

    /**
     * 客户端 ZK
     */
    private ClientZookeeper clientZookeeper;

    public ConfCenterClientConf getClientConf() {
        return clientConf;
    }

    public ZKConfig getZkConfig() {
        return zkConfig;
    }

    public ConfCenterZookeeper getConfCenterZookeeper() {
        return confCenterZookeeper;
    }

    public ZkClient getZkClient() {
        return zkClient;
    }

    public DefaultConfCenterClient(ConfCenterClientConf clientConf) {
        this.clientConf = clientConf;
        this.initZk();
        this.dataStorageManager = new DefaultDataStorageManager();
        this.clientZookeeper = new ClientZookeeper(this.zkClient, this.confCenterZookeeper, this.dataStorageManager);
        clientZookeeper.loadDataFromZk();
    }

    /**
     * 初始化zk
     */
    private void initZk() {
        ZKConfig zkConfig = this.clientConf.getZkConfig();
        if (zkConfig == null) {
            throw new IllegalParameterException("ZK配置不能为空");
        }
        if (StringUtils.isEmpty(zkConfig.getZkConnet())) {
            throw new IllegalParameterException("ZK server地址不能为空");
        }
        if (StringUtils.isEmpty(this.clientConf.getProjectName())) {
            throw new IllegalParameterException("配置中心项目名称不能为空");
        }
        this.zkConfig = zkConfig;
        this.confCenterZookeeper = new ConfCenterZookeeper(this.zkConfig,
                this.zkConfig.getZkRoot(), this.clientConf.getProjectName(),this.zkClient);
        this.zkClient = new ZkClient(this.zkConfig.getZkConnet(), this.zkConfig.getZkSessionTimeoutMs(),
                this.zkConfig.getZkConnectionTimeoutMs(), new ZkUtil.StringSerializer());
    }

    @Override
    public ClientDataSource getDataSource(String sourceKey) {
        return this.dataStorageManager.getDataSource(sourceKey);
    }

    @Override
    public Map<String, ClientDataSource> getAllDataSource() {
        return this.dataStorageManager.getAllDataSource();
    }
}
