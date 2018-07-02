package com.kevin.confcenter.admin.log.handler.user;

import com.kevin.confcenter.admin.log.ServiceContext;
import com.kevin.confcenter.admin.log.handler.AbstracterLogServiceHandler;
import com.kevin.confcenter.admin.log.handler.HandlerName;
import com.kevin.confcenter.common.bean.po.operation.OperationLog;
import com.kevin.confcenter.common.bean.po.operation.User;
import com.kevin.confcenter.common.consts.web.operation.OperationTypeEnum;
import com.kevin.confcenter.common.consts.web.operation.TargetTypeEnum;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @Author: jizhong.zhai
 * @Description:
 * @Date: Created In 2018/6/21 15:37
 */
@Service(value = "userCreateHandler")
public class UserCreateHandler extends AbstracterLogServiceHandler {

    @Override
    public String getName() {
        return HandlerName.USER_CREATE_NAME;
    }

    @Override
    public OperationLog after(ServiceContext context) {
        OperationLog log = super.createLog(context);
        log.setTargetType(TargetTypeEnum.USER.getVal());
        log.setType(OperationTypeEnum.INSERT.getVal());
        Object obj = context.getParams()[0];
        if (!(obj instanceof User)) {
            return null;
        }
        User user = (User) obj;
        String name = user.getUserName();
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        log.setTargetName(name);
        log.setRemark("新建用户");
        if (context.getSuccess()) {
            Object result = context.getResult();
            if (result != null && result instanceof Long) {
                log.setTargetId((Long) result);
            }
        }
        return log;
    }
}
