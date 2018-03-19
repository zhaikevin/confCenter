package com.kevin.confcenter.client.api;

import com.kevin.confcenter.client.api.impl.DefaultConfCenterApi;
import com.kevin.confcenter.common.bean.vo.ConfCenterClientConf;
import com.kevin.confcenter.common.bean.vo.ZKConfig;
import org.junit.Test;

/**
 * @Author: kevin
 * @Description:
 * @Date: Created In 2018/3/19 10:09
 */
public class ConfCenterApiTest {

    @Test
    public void getDataSourceTest() {
        ZKConfig zkConfig = new ZKConfig("127.0.0.1:2181");
        ConfCenterClientConf clientConf = new ConfCenterClientConf(zkConfig,"127.0.0.1:8080","test");
        ConfCenterApi confCenterApi = DefaultConfCenterApi.getInstance(clientConf);
        System.out.println(confCenterApi.getDataSource("testKey"));
    }

}
