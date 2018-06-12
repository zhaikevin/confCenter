package com.kevin.confcenter.admin.log.handler;

import com.kevin.confcenter.admin.log.ServiceContext;
import com.kevin.confcenter.common.bean.po.operation.OperationLog;
import com.kevin.confcenter.common.consts.web.operation.OperationResultEnum;
import org.apache.commons.lang.StringUtils;

import java.util.Date;

public abstract class AbstracterLogServiceHandler implements LogServiceHandler {

    private static final Integer MAX_MSG_LENGTH = 1000;


    @Override
    public void before(ServiceContext context) {

    }

    @Override
    public OperationLog after(ServiceContext context) {
        return null;
    }

    protected OperationLog createLog(ServiceContext context) {
        OperationLog log = new OperationLog();
        //TODO:暂时写死
        log.setUserId(2L);
        log.setCreateTime(new Date());
        String message = context.getMessage();
        if (StringUtils.isNotEmpty(message) && message.length() > MAX_MSG_LENGTH) {
            message = message.substring(0, MAX_MSG_LENGTH - 1);
        }
        log.setErrorMsg(message);
        if(context.getResult()) {
            log.setResult(OperationResultEnum.SUCCESS.getVal());
        } else {
            log.setResult(OperationResultEnum.FAIL.getVal());
        }
        return log;
    }
}
