package com.kevin.confcenter.client.api.impl;

import com.kevin.confcenter.client.api.ConfCenterApi;
import com.kevin.confcenter.client.api.ConfCenterClient;
import com.kevin.confcenter.client.api.DataChangeListener;
import com.kevin.confcenter.common.bean.vo.ClientDataSource;
import com.kevin.confcenter.common.bean.vo.ConfCenterClientConf;
import com.kevin.confcenter.common.bean.vo.ZKConfig;
import com.kevin.confcenter.common.exception.IllegalParameterException;
import com.kevin.confcenter.common.utils.CommonConfigUtil;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: kevin
 * @Description: api实现
 * @Date: Created In 2018/3/10 14:51
 */
public class DefaultConfCenterApi implements ConfCenterApi {

    /**
     * 默认配置文件地址
     */
    private final static String DEFAULT_CONFIG_FILE_NAME = "conf_center.properties";

    /**
     * client
     */
    private static ConfCenterClient client;

    /**
     * instance
     */
    private static volatile DefaultConfCenterApi instance;

    /**
     * 监听器
     */
    private static List<DataChangeListener> listeners = new ArrayList<>();

    private DefaultConfCenterApi() {

    }

    public static DefaultConfCenterApi getInstance() {
        return getInstance(DEFAULT_CONFIG_FILE_NAME);
    }

    public static DefaultConfCenterApi getInstance(String configFileName) {
        ConfCenterClientConf clientConf = getConf(configFileName);
        return getInstance(clientConf);
    }

    public static DefaultConfCenterApi getInstance(ConfCenterClientConf clientConf) {
        if (instance == null) {
            synchronized (DefaultConfCenterApi.class) {
                if (instance == null) {
                    instance = new DefaultConfCenterApi();
                    client = new DefaultConfCenterClient(clientConf,listeners);
                }
            }
        }
        return instance;
    }


    private static ConfCenterClientConf getConf(String fileName) {
        Configuration configuration = CommonConfigUtil.getConfig(fileName);
        String zkConnect = configuration.getString("zkConnect");
        if (StringUtils.isEmpty(zkConnect)) {
            throw new IllegalParameterException("ZK server地址不能为空");
        }
        ZKConfig zkConfig = new ZKConfig(zkConnect);
        String zkRoot = configuration.getString("zkRoot");
        if (StringUtils.isNotEmpty(zkRoot)) {
            zkConfig.setZkRoot(zkRoot);
        }
        String serverUrl = configuration.getString("serverUrl");
        if (StringUtils.isEmpty(serverUrl)) {
            throw new IllegalParameterException("server url不能为空");
        }
        String projectName = configuration.getString("projectName");
        if (StringUtils.isEmpty(projectName)) {
            throw new IllegalParameterException("project name不能为空");
        }
        ConfCenterClientConf clientConf = new ConfCenterClientConf(zkConfig, serverUrl, projectName);
        String heartBeatTimeMs = configuration.getString("heartBeatTimeMs");
        if (StringUtils.isNotEmpty(heartBeatTimeMs)) {
            clientConf.setHeartBeatTimeMs(Integer.valueOf(heartBeatTimeMs));
        }
        return clientConf;
    }

    @Override
    public ClientDataSource getDataSource(String sourceKey) {
        return client.getDataSource(sourceKey);
    }

    @Override
    public Map<String, ClientDataSource> getAllDataSource() {
        return client.getAllDataSource();
    }

    @Override
    public void addListener(DataChangeListener listener) {
        listeners.add(listener);
    }
}
