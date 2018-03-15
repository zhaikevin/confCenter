package com.kevin.confcenter.client.storage;

import com.kevin.confcenter.client.api.DataChangeListener;
import com.kevin.confcenter.common.bean.vo.ClientDataSource;
import com.kevin.confcenter.common.consts.DataChangeTypeEnum;
import com.kevin.confcenter.common.consts.SourceTypeEnum;
import com.kevin.confcenter.common.utils.CommonUtil;
import com.kevin.confcenter.common.utils.threadPool.AsynchronousHandler;
import com.kevin.confcenter.common.utils.threadPool.CommonThreadPool;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultDataStorageManager.class);

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
     * 线程池
     */
    private CommonThreadPool threadPool;

    public DefaultDataStorageManager(List<DataChangeListener> listeners, CommonThreadPool threadPool) {
        this.listeners = listeners;
        this.threadPool = threadPool;
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
        Map<String, ClientDataSource> oldDataSourceMap = this.cloneData(type);
        if (type.equals(SourceTypeEnum.NORMAL.getVal())) {
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
                    executeCallBack(dataSource, DataChangeTypeEnum.ADD.getVal());
                }
            }
            return;
        }
        //全部是删除
        if (CommonUtil.isEmptyMap(dataSourceMap)) {
            if (!CommonUtil.isEmptyMap(oldDataSourceMap)) {
                for (ClientDataSource dataSource : dataSourceMap.values()) {
                    executeCallBack(dataSource, DataChangeTypeEnum.REDUCE.getVal());
                }
            }
            return;
        }
        for (String sourceKey : dataSourceMap.keySet()) {
            //新增
            if (!oldDataSourceMap.containsKey(sourceKey)) {
                executeCallBack(dataSourceMap.get(sourceKey), DataChangeTypeEnum.ADD.getVal());
            } else if (!oldDataSourceMap.get(sourceKey).equals(dataSourceMap.get(sourceKey))) {
                //修改
                executeCallBack(dataSourceMap.get(sourceKey), DataChangeTypeEnum.UPDATE.getVal());
            }
        }
        for (String sourceKey : oldDataSourceMap.keySet()) {
            //删除
            if (!dataSourceMap.containsKey(sourceKey)) {
                executeCallBack(oldDataSourceMap.get(sourceKey), DataChangeTypeEnum.REDUCE.getVal());
            }
        }
    }

    /**
     * 异步执行回调
     *
     * @param dataSource
     * @param changeType
     */
    private void executeCallBack(final ClientDataSource dataSource, final Integer changeType) {
        if (CollectionUtils.isEmpty(listeners)) {
            return;
        }
        for (final DataChangeListener listener : listeners) {
            threadPool.execute(new AsynchronousHandler() {
                @Override
                public void executeAfter(Throwable t) {

                }

                @Override
                public void executeBefore(Thread t) {

                }

                @Override
                public Object call() throws Exception {
                    try {
                        listener.execute(dataSource, changeType);
                    } catch (Exception e) {
                        LOGGER.error("execute call back failed:{}", e.getMessage(), e);
                    }
                    return null;
                }
            });
        }
    }

    /**
     * clone data
     *
     * @param type
     * @return
     */
    private Map<String, ClientDataSource> cloneData(Integer type) {
        Map<String, ClientDataSource> dataSourceMap = new HashMap<>();
        if (type.equals(SourceTypeEnum.NORMAL.getVal())) {
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
