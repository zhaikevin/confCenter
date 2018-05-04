package com.kevin.confcenter.admin.extend;

import com.kevin.confcenter.admin.controller.BaseController;
import com.kevin.confcenter.common.bean.vo.UserSession;
import com.kevin.confcenter.common.exception.SessionTimeOutException;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: kevin
 * @Description: 统一登录拦截器，拦截静态资源
 * @Date: Created In 2018/4/17 15:34
 */
public class StaticLoginInterceptor extends BaseController implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        try {
            UserSession userSession = getUserSession(httpServletRequest);
            return true;
        } catch (SessionTimeOutException e) {
            httpServletResponse.sendRedirect("/user/login");
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
