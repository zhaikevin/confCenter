package com.kevin.confcenter.client.storage;

import com.kevin.confcenter.client.api.DataChangeListener;
import com.kevin.confcenter.common.bean.vo.ClientDataSource;
import com.kevin.confcenter.common.consts.DataChangeTypeEnum;
import com.kevin.confcenter.common.consts.SourceTypeEnum;
import com.kevin.confcenter.common.utils.CommonUtil;
import com.kevin.confcenter.common.utils.ConfCenterZookeeper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: kevin
 * @Description: 数据存储实现
 * @Date: Created In 2018/3/12 17:35
 */
public class DataStorageManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataStorageManager.class);

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

    /**
     * conf zk
     */
    private ConfCenterZookeeper confCenterZookeeper;

    public DataStorageManager(List<DataChangeListener> listeners,
                              ConfCenterZookeeper confCenterZookeeper) {
        this.listeners = listeners;
        this.confCenterZookeeper = confCenterZookeeper;
    }

    /**
     * 获取所有数据源
     *
     * @return
     */
    public Map<String, ClientDataSource> getAllDataSource() {
        Map<String, ClientDataSource> allDataSourceMap = new HashMap<>();
        allDataSourceMap.putAll(normalDataSourceMap);
        allDataSourceMap.putAll(publicDataSourceMap);
        return allDataSourceMap;
    }

    /**
     * 根据key获取对应的数据源
     *
     * @param sourceKey
     * @return
     */
    public ClientDataSource getDataSource(String sourceKey) {
        if (StringUtils.isEmpty(sourceKey)) {
            return null;
        }
        if (normalDataSourceMap.containsKey(sourceKey)) {
            return normalDataSourceMap.get(sourceKey);
        }
        if (publicDataSourceMap.containsKey(sourceKey)) {
            return publicDataSourceMap.get(sourceKey);
        }
        ClientDataSource dataSource = confCenterZookeeper.getDataSource(sourceKey, SourceTypeEnum.NORMAL);
        if (dataSource != null) {
            return dataSource;
        }
        return confCenterZookeeper.getDataSource(sourceKey, SourceTypeEnum.PUBLIC);
    }

    /**
     * 更新数据
     *
     * @param dataSource
     */
    public void updateSourceByKey(ClientDataSource dataSource) {
        if (dataSource.getSourceType().equals(SourceTypeEnum.NORMAL)) {
            normalDataSourceMap.put(dataSource.getSourceKey(), dataSource);
        } else {
            publicDataSourceMap.put(dataSource.getSourceKey(), dataSource);
        }
        executeCallBack(dataSource, DataChangeTypeEnum.UPDATE);
    }

    /**
     * 添加或删除结点
     *
     * @param sourceType
     * @param keys
     */
    public void addOrDeleteSource(SourceTypeEnum sourceType, List<String> keys) {
        if (CollectionUtils.isEmpty(keys)) {
            return;
        }
        Map<String, ClientDataSource> dataSourceMap = normalDataSourceMap;
        if (sourceType.equals(SourceTypeEnum.PUBLIC)) {
            dataSourceMap = publicDataSourceMap;
        }
        Map<String, ClientDataSource> addSourceMap = new HashMap<>();
        Map<String, String> keyMap = new HashMap<>();
        List<String> deleteList = new ArrayList<>();
        for (String key : keys) {
            if (!dataSourceMap.containsKey(key)) {
                //新增
                ClientDataSource dataSource = confCenterZookeeper.getDataSource(key, sourceType);
                addSourceMap.put(key, dataSource);
            }
            keyMap.put(key, key);
        }
        for (String key : dataSourceMap.keySet()) {
            if (!keyMap.containsKey(key)) {
                //删除
                deleteList.add(key);
            }
        }
        if (!CommonUtil.isEmptyMap(addSourceMap)) {
            for (String key : addSourceMap.keySet()) {
                if (sourceType.equals(SourceTypeEnum.PUBLIC)) {
                    publicDataSourceMap.put(key, addSourceMap.get(key));
                } else {
                    normalDataSourceMap.put(key, addSourceMap.get(key));
                }
                executeCallBack(addSourceMap.get(key), DataChangeTypeEnum.ADD);
            }
        }
        if (CollectionUtils.isNotEmpty(deleteList)) {
            for (String key : deleteList) {
                ClientDataSource dataSource;
                if (sourceType.equals(SourceTypeEnum.PUBLIC)) {
                    dataSource = publicDataSourceMap.get(key);
                    publicDataSourceMap.remove(key);
                } else {
                    dataSource = normalDataSourceMap.get(key);
                    normalDataSourceMap.remove(key);
                }
                executeCallBack(dataSource, DataChangeTypeEnum.REDUCE);
            }
        }
    }

    /**
     * 同步所有数据，用于启动时同步
     *
     * @param dataSources
     * @param sourceType
     */
    public void syncAll(List<ClientDataSource> dataSources, SourceTypeEnum sourceType) {
        if (CollectionUtils.isEmpty(dataSources)) {
            return;
        }
        if (sourceType.equals(SourceTypeEnum.NORMAL)) {
            for (ClientDataSource dataSource : dataSources) {
                normalDataSourceMap.put(dataSource.getSourceKey(), dataSource);
            }
        } else {
            for (ClientDataSource dataSource : dataSources) {
                publicDataSourceMap.put(dataSource.getSourceKey(), dataSource);
            }
        }
    }


    public void refresh(List<ClientDataSource> dataSources, SourceTypeEnum sourceType) {
        if (CollectionUtils.isEmpty(dataSources)) {
            return;
        }
        Map<String, ClientDataSource> oldDataSourceMap = this.cloneData(sourceType);
        if (sourceType.equals(SourceTypeEnum.NORMAL)) {
            normalDataSourceMap.clear();
            for (ClientDataSource dataSource : dataSources) {
                normalDataSourceMap.put(dataSource.getSourceKey(), dataSource);
            }
            compareData(oldDataSourceMap, normalDataSourceMap);
        } else {
            publicDataSourceMap.clear();
            for (ClientDataSource dataSource : dataSources) {
                publicDataSourceMap.put(dataSource.getSourceKey(), dataSource);
            }
            compareData(oldDataSourceMap, publicDataSourceMap);
        }
    }

    /**
     * 比较数据
     *
     * @param oldDataSourceMap
     * @param dataSourceMap
     */
    private void compareData(Map<String, ClientDataSource> oldDataSourceMap,
                             Map<String, ClientDataSource> dataSourceMap) {
        //全部是新增
        if (CommonUtil.isEmptyMap(oldDataSourceMap)) {
            if (!CommonUtil.isEmptyMap(dataSourceMap)) {
                for (ClientDataSource dataSource : dataSourceMap.values()) {
                    executeCallBack(dataSource, DataChangeTypeEnum.ADD);
                }
            }
            return;
        }
        //全部是删除
        if (CommonUtil.isEmptyMap(dataSourceMap)) {
            if (!CommonUtil.isEmptyMap(oldDataSourceMap)) {
                for (ClientDataSource dataSource : dataSourceMap.values()) {
                    executeCallBack(dataSource, DataChangeTypeEnum.REDUCE);
                }
            }
            return;
        }
        for (String sourceKey : dataSourceMap.keySet()) {
            //新增
            if (!oldDataSourceMap.containsKey(sourceKey)) {
                executeCallBack(dataSourceMap.get(sourceKey), DataChangeTypeEnum.ADD);
            } else if (!oldDataSourceMap.get(sourceKey).equals(dataSourceMap.get(sourceKey))) {
                //修改
                executeCallBack(dataSourceMap.get(sourceKey), DataChangeTypeEnum.UPDATE);
            }
        }
        for (String sourceKey : oldDataSourceMap.keySet()) {
            //删除
            if (!dataSourceMap.containsKey(sourceKey)) {
                executeCallBack(oldDataSourceMap.get(sourceKey), DataChangeTypeEnum.REDUCE);
            }
        }
    }

    /**
     * 异步执行回调
     *
     * @param dataSource
     * @param changeType
     */
    private void executeCallBack(ClientDataSource dataSource, DataChangeTypeEnum changeType) {
        if (CollectionUtils.isEmpty(listeners)) {
            return;
        }
        for (final DataChangeListener listener : listeners) {
            ExecuteCallBackTask.execute(listener, dataSource, changeType);
        }
    }

    /**
     * clone data
     *
     * @param sourceType
     * @return
     */
    private Map<String, ClientDataSource> cloneData(SourceTypeEnum sourceType) {
        Map<String, ClientDataSource> dataSourceMap = new HashMap<>();
        if (sourceType.equals(SourceTypeEnum.NORMAL)) {
            for (String key : normalDataSourceMap.keySet()) {
                dataSourceMap.put(key, normalDataSourceMap.get(key));
            }
        } else {
            for (String key : publicDataSourceMap.keySet()) {
                dataSourceMap.put(key, publicDataSourceMap.get(key));
            }
        }
        return dataSourceMap;
    }
}
