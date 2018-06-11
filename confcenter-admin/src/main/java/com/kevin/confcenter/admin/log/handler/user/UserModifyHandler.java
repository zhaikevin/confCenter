package com.kevin.confcenter.admin.log.handler.user;

import com.kevin.confcenter.admin.log.ServiceContext;
import com.kevin.confcenter.admin.log.handler.AbstracterLogServiceHandler;
import com.kevin.confcenter.admin.log.handler.HandlerName;
import com.kevin.confcenter.common.exception.BusinessException;
import org.springframework.stereotype.Service;

/**
 * @Author: jizhong.zhai
 * @Description:
 * @Date: Created In 2018/6/11 11:06
 */
@Service(value = "userModifyHandler")
public class UserModifyHandler extends AbstracterLogServiceHandler {

    @Override
    public String getName() {
        return HandlerName.USER_MODIFY_NAME;
    }

    @Override
    public void before(ServiceContext context) {
        //throw new BusinessException("我就试试");
    }
}
