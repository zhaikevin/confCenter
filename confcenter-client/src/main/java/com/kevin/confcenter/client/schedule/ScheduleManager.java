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

    private static final int MAX_FAIL_COUNT = 5;

    private ConfCenterClientConf clientConf;

    private ScheduledExecutorService service;

    private HeartBeatManager heartBeatManager;

    private volatile int failCount = 0;

    public ScheduleManager(ConfCenterClientConf clientConf, HeartBeatManager heartBeatManager) {
        this.clientConf = clientConf;
        this.heartBeatManager = heartBeatManager;
        service = Executors.newSingleThreadScheduledExecutor();
    }

    public void startJob() {
        service.scheduleAtFixedRate(new PingScheduling(), clientConf.getHeartBeatTimeMs(), clientConf.getHeartBeatTimeMs(), TimeUnit.SECONDS);
    }

    /**
     * ping任务定时器
     */
    class PingScheduling implements Runnable {

        @Override
        public void run() {
            Boolean isPingSuccess = false;
            try {
                heartBeatManager.pingToServer();
                isPingSuccess = true;
            } catch (Exception e) {
                LOGGER.warn("ping failed:{}", e.getMessage(), e);
            }
            if (isPingSuccess) {
                failCount = 0;
            } else {
                failCount += 1;
                if (failCount >= MAX_FAIL_COUNT) {
                    //连续ping失败到最大次数，日志告警
                    LOGGER.error("ping to server failed!");
                    failCount = 0;
                }
            }
        }
    }

}
