package com.kevin.confcenter.admin.log.handler;

import com.kevin.confcenter.admin.log.ServiceContext;
import com.kevin.confcenter.common.bean.po.operation.OperationLog;

public interface LogServiceHandler {

    void before(ServiceContext context);

    OperationLog after(ServiceContext context);

    String getName();
}
