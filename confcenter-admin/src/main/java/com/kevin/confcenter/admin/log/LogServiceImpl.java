package com.kevin.confcenter.admin.log;

import com.kevin.confcenter.admin.log.handler.LogServiceHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.util.List;

@Service(value = "logService")
public class LogServiceImpl implements LogService {

    @Autowired
    private List<LogServiceHandler> handlerList;

    @Override
    public void before(ServiceContext context) {
        LogServiceHandler handler = getHandler(context);
        if (handler != null) {
            handler.before(context);
        }
    }

    @Override
    public void after(ServiceContext context) {
        LogServiceHandler handler = getHandler(context);
        if (handler != null) {
            handler.after(context);
        }
    }

    private LogServiceHandler getHandler(ServiceContext context) {
        for (Annotation annotation : context.getAnnotations()) {
            if (!(annotation instanceof Log)) {
                continue;
            }
            Log log = (Log) annotation;
            String handlerName = log.handler();
            for (LogServiceHandler handler : handlerList) {
                if (handler.equals(handlerName)) {
                    return handler;
                }
            }
        }
        return null;
    }
}
