package com.kevin.confcenter.client.storage;

import com.kevin.confcenter.client.api.DataChangeListener;
import com.kevin.confcenter.common.bean.vo.ClientDataSource;
import com.kevin.confcenter.common.consts.DataChangeTypeEnum;
import com.kevin.confcenter.common.utils.ThreadPoolUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: jizhong.zhai
 * @Description:
 * @Date: Created In 2018/7/2 16:46
 */
public class ExecuteCallBackTask implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExecuteCallBackTask.class);

    private DataChangeListener listener;

    private ClientDataSource dataSource;

    private DataChangeTypeEnum changeType;

    public ExecuteCallBackTask(DataChangeListener listener, ClientDataSource dataSource,
                               DataChangeTypeEnum changeType) {
        this.listener = listener;
        this.dataSource = dataSource;
        this.changeType = changeType;
    }

    @Override
    public void run() {
        try {
            listener.execute(dataSource, changeType);
        } catch (Exception e) {
            LOGGER.error("execute call back failed:{}", e.getMessage(), e);
        }
    }

    public static void execute(DataChangeListener listener, ClientDataSource dataSource,
                               DataChangeTypeEnum changeType) {
        ExecuteCallBackTask task = new ExecuteCallBackTask(listener, dataSource, changeType);
        ThreadPoolUtil.execute(task);
    }
}
