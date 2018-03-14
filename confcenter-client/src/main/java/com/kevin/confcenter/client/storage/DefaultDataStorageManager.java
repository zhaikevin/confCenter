package com.kevin.confcenter.client.storage;

import com.kevin.confcenter.client.api.DataChangeListener;
import com.kevin.confcenter.common.bean.vo.ClientDataSource;
import com.kevin.confcenter.common.consts.SourceTypeEnum;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: kevin
 * @Description: 数据存储默认实现
 * @Date: Created In 2018/3/12 17:35
 */
public class DefaultDataStorageManager implements DataStorageManager {

    /**
     * 普通数据源
     */
    private Map<String, ClientDataSource> normalDataSourceMap = new ConcurrentHashMap<>();

    /**
     * 公共数据源
     */
    private Map<String, ClientDataSource> publicDataSourceMap = new ConcurrentHashMap<>();

    /**
     * listeners
     */
    private List<DataChangeListener> listeners;

    public DefaultDataStorageManager(List<DataChangeListener> listeners) {
        this.listeners = listeners;
    }

    @Override
    public Map<String, ClientDataSource> getAllDataSource() {
        Map<String, ClientDataSource> allDataSourceMap = new HashMap<>();
        allDataSourceMap.putAll(normalDataSourceMap);
        allDataSourceMap.putAll(publicDataSourceMap);
        return allDataSourceMap;
    }

    @Override
    public ClientDataSource getDataSource(String sourceKey) {
        if (StringUtils.isEmpty(sourceKey)) {
            return null;
        }
        if (normalDataSourceMap.containsKey(sourceKey)) {
            return normalDataSourceMap.get(sourceKey);
        }
        return publicDataSourceMap.get(sourceKey);
    }

    @Override
    public void refresh(List<ClientDataSource> dataSources, Integer type) {
        if (dataSources == null || dataSources.size() == 0) {
            return;
        }
        if (type.equals(SourceTypeEnum.NORMAL)) {
            normalDataSourceMap.clear();
            for (ClientDataSource dataSource : dataSources) {
                normalDataSourceMap.put(dataSource.getSourceKey(), dataSource);
            }
        } else {
            publicDataSourceMap.clear();
            for (ClientDataSource dataSource : dataSources) {
                publicDataSourceMap.put(dataSource.getSourceKey(), dataSource);
            }
        }
    }
}
