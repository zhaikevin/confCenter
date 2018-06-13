package com.kevin.confcenter.admin.extend;

import com.kevin.confcenter.common.bean.vo.UserCookie;
import com.kevin.confcenter.common.exception.AuthFailedException;
import com.kevin.confcenter.common.utils.PropertyCopyUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: kevin
 * @Description: 统一登录拦截器，拦截数据请求
 * @Date: Created In 2018/4/17 15:34
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        Cookie[] cookies = httpServletRequest.getCookies();
        Map<String, Object> map = new HashMap<>();
        for (Cookie cookie : cookies) {
            map.put(cookie.getName(), cookie.getValue());
        }
        UserCookie userCookie = PropertyCopyUtil.copyPropertyFromMap(UserCookie.class, map);
        AuthHelper.setUserCookie(userCookie);
        if (AuthHelper.loginCheck()) {
            return true;
        }
        throw new AuthFailedException("校验失败，请重新登录");
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
        AuthHelper.removeUserCookie();
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
