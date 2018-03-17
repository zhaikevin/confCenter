package com.kevin.confcenter.client.schedule;

import com.kevin.confcenter.common.bean.vo.ConfCenterClientConf;

/**
 * @Author: kevin
 * @Description: 心跳
 * @Date: Created In 2018/3/17 11:13
 */
public class HeartBeatManager {

    private ConfCenterClientConf clientConf;

    public HeartBeatManager(ConfCenterClientConf clientConf) {
        this.clientConf = clientConf;
    }

    /**
     * 向服务端发送请求
     *
     * @return
     */
    public Boolean pingToServer() {
        return false;
    }
}
