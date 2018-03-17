package com.kevin.confcenter.client.schedule;

import com.kevin.confcenter.common.bean.vo.ConfCenterClientConf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Author: kevin
 * @Description: 定时任务管理
 * @Date: Created In 2018/3/15 17:15
 */
public class ScheduleManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleManager.class);

    private ConfCenterClientConf clientConf;

    private ScheduledExecutorService service;

    private HeartBeatManager heartBeatManager;

    public ScheduleManager(ConfCenterClientConf clientConf, HeartBeatManager heartBeatManager) {
        this.clientConf = clientConf;
        this.heartBeatManager = heartBeatManager;
        service = Executors.newSingleThreadScheduledExecutor();
    }

    public void startJob() {
        service.scheduleAtFixedRate(new PingScheduling(),clientConf.getHeartBeatTimeMs(),clientConf.getHeartBeatTimeMs(), TimeUnit.SECONDS);
    }

    /**
     * ping任务定时器
     */
    class PingScheduling implements Runnable {

        @Override
        public void run() {

        }
    }

}
