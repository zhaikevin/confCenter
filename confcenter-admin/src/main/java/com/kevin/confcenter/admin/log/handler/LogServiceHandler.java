package com.kevin.confcenter.admin.log.handler;

import com.kevin.confcenter.admin.log.ServiceContext;

public interface LogServiceHandler {

    void before(ServiceContext context);

    void after(ServiceContext context);

    String getName();
}
