package com.kevin.confcenter.admin.log.handler.user;

import com.kevin.confcenter.admin.extend.SpringUtil;
import com.kevin.confcenter.admin.log.ServiceContext;
import com.kevin.confcenter.admin.log.handler.AbstracterLogServiceHandler;
import com.kevin.confcenter.admin.service.operation.UserService;
import com.kevin.confcenter.common.bean.po.operation.OperationLog;
import com.kevin.confcenter.common.bean.po.operation.User;
import com.kevin.confcenter.common.consts.web.operation.OperationTypeEnum;
import com.kevin.confcenter.common.consts.web.operation.TargetTypeEnum;

/**
 * @Author: jizhong.zhai
 * @Description:
 * @Date: Created In 2018/6/12 10:39
 */
public abstract class AbstracterUserHandler extends AbstracterLogServiceHandler {

    private UserService userService = SpringUtil.getBean("userService", UserService.class);

    @Override
    public void before(ServiceContext context) {
        Object obj = context.getParams()[0];
        Long id = null;
        if (obj instanceof User) {
            User newUser = (User) obj;
            id = newUser.getId();
        } else if (obj instanceof Long) {
            id = (Long) obj;
        }
        User user = userService.detail(id);
        context.setAttribute(user);
    }

    @Override
    protected OperationLog createLog(ServiceContext context) {
        OperationLog log = super.createLog(context);
        log.setTargetType(TargetTypeEnum.USER.getVal());
        log.setType(OperationTypeEnum.MODIFY.getVal());
        Object attribute = context.getAttribute();
        if(attribute instanceof User) {
            User oldUser = (User) attribute;
            log.setTargetId(oldUser.getId());
            log.setTargetName(oldUser.getUserName());
        }
        return log;
    }
}
