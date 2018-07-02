package com.kevin.confcenter.client.api.impl;

import com.kevin.confcenter.client.api.ConfCenterClient;
import com.kevin.confcenter.client.api.DataChangeListener;
import com.kevin.confcenter.client.schedule.HeartBeatManager;
import com.kevin.confcenter.client.schedule.ScheduleManager;
import com.kevin.confcenter.client.storage.ClientZookeeper;
import com.kevin.confcenter.client.storage.DataStorageManager;
import com.kevin.confcenter.common.bean.vo.ClientDataSource;
import com.kevin.confcenter.common.bean.vo.ConfCenterClientConf;
import com.kevin.confcenter.common.bean.vo.ZKConfig;
import com.kevin.confcenter.common.exception.IllegalParameterException;
import com.kevin.confcenter.common.utils.ConfCenterZookeeper;
import com.kevin.confcenter.common.utils.ZkUtil;
import org.I0Itec.zkclient.ZkClient;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * @Author: kevin
 * @Description: 客户端默认实现
 * @Date: Created In 2018/3/6 9:33
 */
public class DefaultConfCenterClient implements ConfCenterClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultConfCenterApi.class);

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

    /**
     * listeners
     */
    private List<DataChangeListener> listeners;

    /**
     * 调度
     */
    private ScheduleManager scheduleManager;


    public DefaultConfCenterClient(ConfCenterClientConf clientConf, List<DataChangeListener> listeners) {
        this.clientConf = clientConf;
        this.listeners = listeners;
        this.initZk();
        dataStorageManager = new DataStorageManager(this.listeners, confCenterZookeeper);
        clientZookeeper = new ClientZookeeper(zkClient, confCenterZookeeper, dataStorageManager);
        clientZookeeper.loadDataFromZk();
        HeartBeatManager heartBeatManager = new HeartBeatManager(this.clientConf);
        scheduleManager = new ScheduleManager(this.clientConf, heartBeatManager);
        scheduleManager.startJob();
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
        this.zkClient = new ZkClient(this.zkConfig.getZkConnet(), this.zkConfig.getZkSessionTimeoutMs(),
                this.zkConfig.getZkConnectionTimeoutMs(), new ZkUtil.StringSerializer());
        this.confCenterZookeeper = new ConfCenterZookeeper(this.zkConfig,
                this.zkConfig.getZkRoot(), this.clientConf.getProjectName(), this.zkClient);
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
