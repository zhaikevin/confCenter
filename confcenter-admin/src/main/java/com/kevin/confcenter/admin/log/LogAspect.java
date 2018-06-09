package com.kevin.confcenter.admin.log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 日志切面
 */
@Aspect
@Component
public class LogAspect {

    @Autowired
    private LogService logService;

    /**
     * 切点
     */
    @Pointcut("@annotation(com.kevin.confcenter.admin.log.Log)")
    public void logAspect() {

    }

    @Around("logAspect()")
    public void around(ProceedingJoinPoint pjp) {
        MethodSignature msig = ((MethodSignature) pjp.getSignature());
        Method method = null;
        try {
            method = pjp.getTarget().getClass().getMethod(msig.getName(), msig.getParameterTypes());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        Object[] args = pjp.getArgs();
        ServiceContext context = createServiceContext(method, args);
        logService.before(context);
        try {
            pjp.proceed();
            context.setResult(true);
        } catch (Throwable e) {
            context.setResult(false);
        }
        logService.after(context);
    }

    private ServiceContext createServiceContext(Method method, Object[] args) {
        ServiceContext context = new ServiceContext();
        context.setParams(args);
        context.setAnnotations(method.getAnnotations());
        return context;
    }
}
