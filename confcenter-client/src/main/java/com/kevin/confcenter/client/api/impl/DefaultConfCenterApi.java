package com.kevin.confcenter.client.api.impl;

import com.kevin.confcenter.client.api.ConfCenterApi;
import com.kevin.confcenter.client.api.ConfCenterClient;
import com.kevin.confcenter.common.bean.vo.ClientDataSource;
import com.kevin.confcenter.common.bean.vo.ConfCenterClientConf;
import com.kevin.confcenter.common.bean.vo.ZKConfig;
import com.kevin.confcenter.common.exception.IllegalParameterException;
import com.kevin.confcenter.common.utils.CommonConfigUtil;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.StringUtils;

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
    private ConfCenterClient client;


    public DefaultConfCenterApi() {
        this(DEFAULT_CONFIG_FILE_NAME);
    }

    public DefaultConfCenterApi(String configFileName) {
        ConfCenterClientConf clientConf = this.getConf(configFileName);
        this.client = new DefaultConfCenterClient(clientConf);
    }

    public DefaultConfCenterApi(ConfCenterClientConf clientConf) {
        this.client = new DefaultConfCenterClient(clientConf);
    }

    private ConfCenterClientConf getConf(String fileName) {
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
        return this.client.getDataSource(sourceKey);
    }
}
