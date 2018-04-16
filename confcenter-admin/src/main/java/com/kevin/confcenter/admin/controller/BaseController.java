package com.kevin.confcenter.admin.controller;

import com.kevin.confcenter.common.bean.po.operation.User;
import com.kevin.confcenter.common.bean.vo.UserSession;
import com.kevin.confcenter.common.exception.SessionTimeOutException;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: kevin
 * @Description:
 * @Date: Created In 2018/4/16 14:18
 */
public abstract class BaseController {

    private static final String USER_SESSION_KEY = "user";

    /**
     * 保存session
     *
     * @param request
     * @param user
     */
    public void setUserSession(HttpServletRequest request, User user) {
        UserSession userSession = new UserSession();
        userSession.setId(user.getId());
        userSession.setType(user.getType());
        userSession.setUserName(user.getUserName());
        request.getSession().setAttribute(USER_SESSION_KEY, user);
    }

    /**
     * 获取session
     *
     * @param request
     * @return
     */
    public UserSession getUserSession(HttpServletRequest request) {
        Object object = request.getSession().getAttribute(USER_SESSION_KEY);
        if (object != null && object instanceof UserSession) {
            return (UserSession) object;
        }
        throw new SessionTimeOutException();
    }

    /**
     * 清除session
     *
     * @param request
     */
    public void deleteSession(HttpServletRequest request) {
        request.getSession().removeAttribute(USER_SESSION_KEY);
        request.getSession().invalidate();
    }
}
