package com.kevin.confcenter.admin.log.task;

import com.kevin.confcenter.admin.log.LogService;
import com.kevin.confcenter.admin.log.ServiceContext;
import com.kevin.confcenter.common.utils.ThreadPoolUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Future;

/**
 * @Author: jizhong.zhai
 * @Description:
 * @Date: Created In 2018/7/2 16:15
 */
public class AfterTask implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(AfterTask.class);

    private LogService logService;

    private Future<ServiceContext> future;

    private boolean isSuccess;

    private Object result;

    public AfterTask(LogService logService, Future<ServiceContext> future,
                     boolean isSuccess, Object result) {
        this.logService = logService;
        this.future = future;
        this.isSuccess = isSuccess;
        this.result = result;
    }

    @Override
    public void run() {
        try {
            ServiceContext context = future.get();
            context.setSuccess(isSuccess);
            if (isSuccess) {
                context.setResult(result);
            } else {
                context.setMessage(String.valueOf(result));
            }
            logService.after(context);
        } catch (Exception e) {
            LOGGER.error("log aspect handle after:{}", e.getMessage(), e);
        }
    }

    public static void execute(LogService logService, Future<ServiceContext> future,
                        boolean isSuccess, Object result) {
        AfterTask task = new AfterTask(logService, future, isSuccess, result);
        ThreadPoolUtil.execute(task);
    }
}
