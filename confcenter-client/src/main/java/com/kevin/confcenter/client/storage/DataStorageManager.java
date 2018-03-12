package com.kevin.confcenter.client.storage;

import com.kevin.confcenter.common.bean.vo.ClientDataSource;

import java.util.List;
import java.util.Map;

/**
 * @Author: kevin
 * @Description: 保存数据manager
 * @Date: Created In 2018/3/12 17:31
 */
public interface DataStorageManager {

    /**
     * 获取所有的数据源
     *
     * @return
     */
    Map<String, ClientDataSource> getAllDataSource();

    /**
     * 根据key获取数据源
     *
     * @param sourceKey
     * @return
     */
    ClientDataSource getDataSource(String sourceKey);

    /**
     * 刷新所有数据源
     *
     * @param dataSources 数据源列表
     * @param sourceType  数据源类型
     */
    void refresh(List<ClientDataSource> dataSources, Integer sourceType);
}
