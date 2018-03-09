package com.kevin.confcenter.client.api.impl;

import com.kevin.confcenter.common.bean.vo.ConfCenterClientConf;
import com.kevin.confcenter.common.bean.vo.ZKConfig;
import com.kevin.confcenter.client.api.ClientFactory;
import com.kevin.confcenter.common.utils.ConfCenterZookeeper;

/**
 * @Author: kevin
 * @Description: 默认实现
 * @Date: Created In 2018/3/6 9:29
 */
public class DefaultClientFactory implements ClientFactory{

    /**
     * 客户端配置
     */
    private ConfCenterClientConf clientConf;

    /**
     * ZK配置
     */
    private ZKConfig zkConfig;

    /**
     * ZK
     */
    private ConfCenterZookeeper confCenterZookeeper;

    public ConfCenterClientConf getClientConf() {
        return clientConf;
    }

    public ZKConfig getZkConfig() {
        return zkConfig;
    }

    public ConfCenterZookeeper getConfCenterZookeeper() {
        return confCenterZookeeper;
    }

    public DefaultClientFactory(ConfCenterClientConf clientConf) {
        this.clientConf = clientConf;
        this.zkConfig = clientConf.getZkConfig();
        this.confCenterZookeeper = new ConfCenterZookeeper(this.zkConfig,
                this.zkConfig.getZkRoot(),this.clientConf.getProjectName());
    }


    @Override
    public void createClient() {

    }
}
