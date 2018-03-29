package com.kevin.confcenter.client.api;

import com.kevin.confcenter.common.bean.vo.ClientDataSource;
import com.kevin.confcenter.common.consts.DataChangeTypeEnum;

/**
 * @Author: kevin
 * @Description: 监听数据变化回调接口
 * @Date: Created In 2018/3/14 16:38
 */
public interface DataChangeListener {

    /**
     * 执行回调函数
     *
     * @param dataSource 数据源
     * @param changeType 改变类型，{@link com.kevin.confcenter.common.consts.DataChangeTypeEnum}
     */
    void execute(ClientDataSource dataSource, DataChangeTypeEnum changeType);
}
