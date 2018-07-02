package com.kevin.confcenter.admin.log;

import com.kevin.confcenter.admin.extend.AuthHelper;
import com.kevin.confcenter.admin.log.task.AfterTask;
import com.kevin.confcenter.admin.log.task.BeforeTask;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

/**
 * 日志切面
 */
@Aspect
@Component
public class LogAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogAspect.class);

    @Autowired
    private LogService logService;

    private ThreadLocal<Future<ServiceContext>> local = new ThreadLocal<>();

    /**
     * 切点
     */
    @Pointcut("@annotation(com.kevin.confcenter.admin.log.Log)")
    public void logAspect() {

    }

    @Before("logAspect()")
    public void before(JoinPoint joinPoint) {
        try {
            Long userId = AuthHelper.getUserId();
            Future<ServiceContext> future = BeforeTask.submit(logService, joinPoint, userId);
            local.set(future);
        } catch (Exception e) {
            LOGGER.error("log aspect handle before:{}", e.getMessage(), e);
        }
    }

    @AfterReturning(value = "logAspect()", returning = "result")
    public void after(JoinPoint joinPoint, Object result) {
        Future<ServiceContext> future = local.get();
        local.remove();
        if (future == null) {
            return;
        }
        try {
            AfterTask.execute(logService, future, true, result);
        } catch (Exception e) {
            LOGGER.error("log aspect handle after:{}", e.getMessage(), e);
        }
    }

    @AfterThrowing(value = "logAspect()", throwing = "throwable")
    public void exception(Throwable throwable) {
        Future<ServiceContext> future = local.get();
        local.remove();
        if (future == null) {
            return;
        }
        try {
            AfterTask.execute(logService, future, false, throwable.getMessage());
        } catch (Exception e) {
            LOGGER.error("log aspect handle after:{}", e.getMessage(), e);
        }
    }
}
