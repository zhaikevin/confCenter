package com.kevin.confcenter.admin.log;

public interface LogService {

    void before(ServiceContext context);

    void after(ServiceContext context);
}
