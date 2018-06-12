package com.kevin.confcenter.admin.log.handler.user;

import com.kevin.confcenter.admin.log.ServiceContext;
import com.kevin.confcenter.admin.log.handler.AbstracterLogServiceHandler;
import com.kevin.confcenter.admin.log.handler.HandlerName;
import com.kevin.confcenter.common.bean.po.operation.OperationLog;
import org.springframework.stereotype.Service;

/**
 * @Author: jizhong.zhai
 * @Description:
 * @Date: Created In 2018/6/12 11:14
 */
@Service(value = "userEnableHandler")
public class UserEnableHandler extends UserHandler {

    @Override
    public String getName() {
        return HandlerName.USER_ENABLE_NAME;
    }

    @Override
    public OperationLog after(ServiceContext context) {
        OperationLog log = super.createLog(context);
        log.setRemark("启用用户");
        return log;
    }
}
