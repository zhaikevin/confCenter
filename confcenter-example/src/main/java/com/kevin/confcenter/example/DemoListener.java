package com.kevin.confcenter.example;

import com.kevin.confcenter.client.api.DataChangeListener;
import com.kevin.confcenter.common.bean.vo.ClientDataSource;
import com.kevin.confcenter.common.consts.DataChangeTypeEnum;

/**
 * @Author: kevin
 * @Description: listener Demo
 * @Date: Created In 2018/3/26 9:14
 */
public class DemoListener implements DataChangeListener {

    @Override
    public void execute(ClientDataSource dataSource, Integer changeType) {
        if (dataSource.getSourceKey() != "testKey") {
            return;
        }
        if (changeType.equals(DataChangeTypeEnum.UPDATE)) {
            System.out.println(dataSource.getSourceValue());
        }
    }
}
