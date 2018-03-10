package com.kevin.confcenter.client.api.impl;

import com.kevin.confcenter.client.api.ConfCenterClient;
import com.kevin.confcenter.common.bean.vo.ClientDataSource;

/**
 * @Author: kevin
 * @Description: 客户端默认实现
 * @Date: Created In 2018/3/6 9:33
 */
public class DefaultConfCenterClient implements ConfCenterClient {

    @Override
    public ClientDataSource getDataSource(String sourceKey) {
        return null;
    }
}
