package com.kevin.confcenter.admin.log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 日志切面
 */
@Aspect
@Component
public class LogAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogAspect.class);

    @Autowired
    private LogService logService;

    private ThreadLocal<ServiceContext> local = new ThreadLocal<>();

    /**
     * 切点
     */
    @Pointcut("@annotation(com.kevin.confcenter.admin.log.Log)")
    public void logAspect() {

    }

    @Before("logAspect()")
    public void before(JoinPoint joinPoint) {
        try {
            Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
            Object[] args = joinPoint.getArgs();
            ServiceContext context = createServiceContext(method, args);
            logService.before(context);
            local.set(context);
        } catch (Exception e) {
            LOGGER.error("log aspect handle before:{}", e.getMessage(), e);
        }
    }

    @AfterReturning("logAspect()")
    public void after(JoinPoint joinPoint) {
        ServiceContext context = local.get();
        if (context != null) {
            context.setResult(true);
            try {
                logService.after(context);
            } catch (Exception e) {
                LOGGER.error("log aspect handle after:{}", e.getMessage(), e);
            }
        }
    }

    @AfterThrowing(value = "logAspect()", throwing = "throwable")
    public void exception(Throwable throwable) {
        ServiceContext context = local.get();
        if (context != null) {
            context.setResult(false);
            context.setMessage(throwable.getMessage());
            try {
                logService.after(context);
            } catch (Exception e) {
                LOGGER.error("log aspect handle after:{}", e.getMessage(), e);
            }
        }
    }

    private ServiceContext createServiceContext(Method method, Object[] args) {
        ServiceContext context = new ServiceContext();
        context.setParams(args);
        context.setAnnotations(method.getAnnotations());
        return context;
    }
}
