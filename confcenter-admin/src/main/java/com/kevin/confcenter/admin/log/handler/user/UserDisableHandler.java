package com.kevin.confcenter.admin.log.handler.user;

import com.kevin.confcenter.admin.log.ServiceContext;
import com.kevin.confcenter.admin.log.handler.HandlerName;
import com.kevin.confcenter.common.bean.po.operation.OperationLog;
import org.springframework.stereotype.Service;

/**
 * @Author: jizhong.zhai
 * @Description:
 * @Date: Created In 2018/6/12 11:41
 */
@Service(value = "userDisabledHandler")
public class UserDisableHandler extends UserHandler {

    @Override
    public String getName() {
        return HandlerName.USER_DISABLE_NAME;
    }

    @Override
    public OperationLog after(ServiceContext context) {
        OperationLog log = super.createLog(context);
        log.setRemark("禁用用户");
        return log;
    }
}
