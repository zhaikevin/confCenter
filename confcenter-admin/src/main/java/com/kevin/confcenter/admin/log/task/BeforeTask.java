package com.kevin.confcenter.admin.log.task;

import com.kevin.confcenter.admin.log.LogService;
import com.kevin.confcenter.admin.log.ServiceContext;
import com.kevin.confcenter.common.utils.ThreadPoolUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * @Author: jizhong.zhai
 * @Description:
 * @Date: Created In 2018/7/2 16:09
 */
public class BeforeTask implements Callable<ServiceContext> {

    private JoinPoint joinPoint;

    private LogService logService;

    private Long userId;

    public BeforeTask(LogService logService, JoinPoint joinPoint, Long userId) {
        this.joinPoint = joinPoint;
        this.logService = logService;
        this.userId = userId;
    }

    @Override
    public ServiceContext call() throws Exception {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Object[] args = joinPoint.getArgs();
        ServiceContext context = createServiceContext(method, args, userId);
        logService.before(context);
        return context;
    }

    private ServiceContext createServiceContext(Method method, Object[] args, Long userId) {
        ServiceContext context = new ServiceContext();
        context.setParams(args);
        context.setAnnotations(method.getAnnotations());
        context.setUserId(userId);
        return context;
    }

    public static Future<ServiceContext> submit(LogService logService, JoinPoint joinPoint, Long userId) {
        BeforeTask task = new BeforeTask(logService,joinPoint,userId);
        return ThreadPoolUtil.submit(task);
    }
}
