package com.kevin.confcenter.admin.extend;

import com.alibaba.fastjson.JSON;
import com.kevin.confcenter.admin.controller.BaseController;
import com.kevin.confcenter.common.bean.vo.ResultInfo;
import com.kevin.confcenter.common.bean.vo.UserSession;
import com.kevin.confcenter.common.exception.SessionTimeOutException;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @Author: kevin
 * @Description: 统一登录拦截器，拦截数据请求
 * @Date: Created In 2018/4/17 15:34
 */
public class ActionLoginInterceptor extends BaseController implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        try {
            UserSession userSession = getUserSession(httpServletRequest);
            return true;
        } catch (SessionTimeOutException e) {
            ResultInfo result = ResultInfo.sessionTimeout();
            this.printToJson(JSON.toJSONString(result), httpServletResponse);
            return false;
        }
    }

    private void printToJson(String json, HttpServletResponse response) {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/json");
            response.setDateHeader("Expires", 0);
            PrintWriter out = response.getWriter();
            out.print(json);
            out.flush();
            out.close();
        } catch (Exception e) {

        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
