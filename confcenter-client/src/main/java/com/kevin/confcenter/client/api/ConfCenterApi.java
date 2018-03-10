package com.kevin.confcenter.client.api;

import com.kevin.confcenter.common.bean.vo.ClientDataSource;

/**
 * @Author: kevin
 * @Description: api
 * @Date: Created In 2018/3/10 14:50
 */
public interface ConfCenterApi {

    /**
     * 根据key获取数据源信息
     *
     * @param sourceKey 数据源key
     * @return
     */
    ClientDataSource getDataSource(String sourceKey);

}
