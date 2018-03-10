package com.kevin.confcenter.client.api.impl;

import com.kevin.confcenter.client.api.ClientFactory;
import com.kevin.confcenter.client.api.ConfCenterApi;
import com.kevin.confcenter.client.api.ConfCenterClient;
import com.kevin.confcenter.common.bean.vo.ClientDataSource;
import com.kevin.confcenter.common.bean.vo.ConfCenterClientConf;

/**
 * @Author: kevin
 * @Description: api实现
 * @Date: Created In 2018/3/10 14:51
 */
public class DefaultConfCenterApi implements ConfCenterApi {

    /**
     * client
     */
    private ConfCenterClient client;

    public DefaultConfCenterApi() {
        ClientFactory factory = new DefaultClientFactory();
        this.client = factory.createClient();
    }

    public DefaultConfCenterApi(ConfCenterClientConf clientConf) {
        ClientFactory factory = new DefaultClientFactory(clientConf);
        this.client = factory.createClient();
    }

    @Override
    public ClientDataSource getDataSource(String sourceKey) {
        return this.client.getDataSource(sourceKey);
    }
}
