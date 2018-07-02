package com.kevin.confcenter.admin.log;

import com.kevin.confcenter.admin.extend.AuthHelper;
import com.kevin.confcenter.common.utils.threadPool.AsynchronousHandler;
import com.kevin.confcenter.common.utils.threadPool.CommonThreadPool;
import com.kevin.confcenter.common.utils.threadPool.ThreadPoolAdaptor;
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

    private ThreadLocal<ThreadPoolAdaptor<ServiceContext>> local = new ThreadLocal<>();

    /**
     * 线程池
     */
    private CommonThreadPool threadPool = CommonThreadPool.getInstance();

    /**
     * 切点
     */
    @Pointcut("@annotation(com.kevin.confcenter.admin.log.Log)")
    public void logAspect() {

    }

    @Before("logAspect()")
    public void before(JoinPoint joinPoint) {
        try {
            ThreadPoolAdaptor adaptor = asynBefore(joinPoint);
            local.set(adaptor);
        } catch (Exception e) {
            LOGGER.error("log aspect handle before:{}", e.getMessage(), e);
        }
    }

    @AfterReturning(value = "logAspect()", returning = "result")
    public void after(JoinPoint joinPoint, Object result) {
        ThreadPoolAdaptor<ServiceContext> adaptor = local.get();
        local.remove();
        if (adaptor == null) {
            return;
        }
        try {
            asynAfter(adaptor, true, result);
        } catch (Exception e) {
            LOGGER.error("log aspect handle after:{}", e.getMessage(), e);
        }
    }

    @AfterThrowing(value = "logAspect()", throwing = "throwable")
    public void exception(Throwable throwable) {
        ThreadPoolAdaptor<ServiceContext> adaptor = local.get();
        local.remove();
        if (adaptor == null) {
            return;
        }
        try {
            asynAfter(adaptor, false, throwable.getMessage());
        } catch (Exception e) {
            LOGGER.error("log aspect handle after:{}", e.getMessage(), e);
        }
    }

    /**
     * 异步执行
     *
     * @param joinPoint
     * @return
     */
    private ThreadPoolAdaptor asynBefore(JoinPoint joinPoint) {
        Long userId = AuthHelper.getUserId();
        ThreadPoolAdaptor adaptor = threadPool.execute(new AsynchronousHandler<ServiceContext>() {
            @Override
            public void executeAfter(Throwable t) {

            }

            @Override
            public void executeBefore(Thread t) {

            }

            @Override
            public ServiceContext call() throws Exception {
                Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
                Object[] args = joinPoint.getArgs();
                ServiceContext context = createServiceContext(method, args, userId);
                logService.before(context);
                return context;
            }
        });
        return adaptor;
    }

    private void asynAfter(ThreadPoolAdaptor<ServiceContext> adaptor, boolean isSuccess, Object result) {
        threadPool.execute(new AsynchronousHandler<Object>() {
            @Override
            public Object call() throws Exception {
                try {
                    ServiceContext context = adaptor.getResult();
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
                return null;
            }

            @Override
            public void executeAfter(Throwable t) {

            }

            @Override
            public void executeBefore(Thread t) {

            }
        });
    }

    private ServiceContext createServiceContext(Method method, Object[] args, Long userId) {
        ServiceContext context = new ServiceContext();
        context.setParams(args);
        context.setAnnotations(method.getAnnotations());
        context.setUserId(userId);
        return context;
    }
}
