package com.kevin.confcenter.example;

import com.alibaba.fastjson.JSONObject;
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
        if (changeType.equals(DataChangeTypeEnum.UPDATE.getVal())) {
            System.out.println("update data:" + JSONObject.toJSONString(dataSource));
        }
        if (changeType.equals(DataChangeTypeEnum.ADD.getVal())) {
            System.out.println("add data:" + JSONObject.toJSONString(dataSource));
        }
        if (changeType.equals(DataChangeTypeEnum.REDUCE.getVal())) {
            System.out.println("reduce data:" + JSONObject.toJSONString(dataSource));
        }
    }
}
