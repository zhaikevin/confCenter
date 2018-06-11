package com.kevin.confcenter.admin.log.handler.user;

import com.kevin.confcenter.admin.log.ServiceContext;
import com.kevin.confcenter.admin.log.handler.AbstracterLogServiceHandler;
import com.kevin.confcenter.admin.log.handler.HandlerName;
import com.kevin.confcenter.admin.service.operation.UserService;
import com.kevin.confcenter.common.bean.po.operation.OperationLog;
import com.kevin.confcenter.common.bean.po.operation.User;
import com.kevin.confcenter.common.consts.web.operation.OperationTypeEnum;
import com.kevin.confcenter.common.consts.web.operation.TargetTypeEnum;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: jizhong.zhai
 * @Description:
 * @Date: Created In 2018/6/11 11:06
 */
@Service(value = "userModifyHandler")
public class UserModifyHandler extends AbstracterLogServiceHandler {

    @Autowired
    private UserService userService;

    @Override
    public String getName() {
        return HandlerName.USER_MODIFY_NAME;
    }

    @Override
    public void before(ServiceContext context) {
        Object obj = context.getParams()[0];
        if (obj instanceof User) {
            User newUser = (User) obj;
            User user = userService.detail(newUser.getId());
            context.setAttribute(user);
        }
    }

    @Override
    public OperationLog after(ServiceContext context) {
        Object param = context.getParams()[0];
        Object attribute = context.getAttribute();
        if (!(param instanceof User) || !(attribute instanceof User)) {
            return null;
        }
        User newUser = (User) param;
        User oldUser = (User) attribute;
        OperationLog log = super.createLog(context);
        log.setTargetId(oldUser.getId());
        log.setTargetName(oldUser.getUserName());
        log.setTargetType(TargetTypeEnum.USER.getVal());
        log.setType(OperationTypeEnum.MODIFY.getVal());
        //TODO:暂时写死
        log.setUserId(2L);
        log.setRemark(generateRemark(newUser, oldUser));
        return log;
    }

    private String generateRemark(User newUser, User oldUser) {
        StringBuilder sb = new StringBuilder();
        List<String> changes = new ArrayList<>();
        StringBuilder change = new StringBuilder();
        if (StringUtils.isNotEmpty(newUser.getUserName()) &&
                !newUser.getUserName().equals(oldUser.getUserName())) {
            change.append("会员姓名从").append(oldUser.getUserName())
                    .append("修改为").append(newUser.getUserName());
        }
        if (changes.size() > 0) {
            sb.append("修改用户信息，");
            for (int i = 0; i < changes.size(); i++) {
                sb.append(changes.get(i));
                if (i < changes.size() - 1) {
                    sb.append("、");
                }
            }
        }

        return sb.toString();
    }
}
