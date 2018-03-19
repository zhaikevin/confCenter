package com.kevin.confcenter.client.schedule;

import com.alibaba.fastjson.JSON;
import com.kevin.confcenter.common.bean.vo.ConfCenterClientConf;
import com.kevin.confcenter.common.bean.vo.ResultInfo;
import com.kevin.confcenter.common.consts.Consts;
import com.kevin.confcenter.common.exception.RemoteCallException;
import com.kevin.confcenter.common.utils.HttpUtil;
import com.kevin.confcenter.common.utils.LocalUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: kevin
 * @Description: 心跳
 * @Date: Created In 2018/3/17 11:13
 */
public class HeartBeatManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(HeartBeatManager.class);

    private ConfCenterClientConf clientConf;

    public HeartBeatManager(ConfCenterClientConf clientConf) {
        this.clientConf = clientConf;
    }

    /**
     * 向服务端发送请求
     *
     * @return
     */
    public void pingToServer() {
        String localIp = LocalUtil.getLocalIp();
        Map<String, Object> params = new HashMap<>();
        params.put(Consts.PING_PARAM_IP, localIp);
        params.put(Consts.PING_PARAM_PROJECT, clientConf.getProjectName());
        String body = JSON.toJSONString(params);
        String url = clientConf.getServerUrl() + Consts.PING_SERVER_URL;
        String response = HttpUtil.doPost(url, body);
        ResultInfo resultInfo = JSON.parseObject(response, ResultInfo.class);
        if (resultInfo.getStatus() != Consts.RESULT_CODE_SUCCESS) {
            throw new RemoteCallException(resultInfo.getStatusInfo());
        }
    }
}
