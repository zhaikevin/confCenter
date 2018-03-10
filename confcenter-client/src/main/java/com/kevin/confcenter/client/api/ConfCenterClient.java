package com.kevin.confcenter.client.api;

import com.kevin.confcenter.common.bean.vo.ClientDataSource;

/**
 * @Author: kevin
 * @Description: 配置中心客户端
 * @Date: Created In 2018/3/6 9:32
 */
public interface ConfCenterClient {

    /**
     * 根据key获取数据源信息
     *
     * @param sourceKey 数据源key
     * @return
     */
    ClientDataSource getDataSource(String sourceKey);
}
