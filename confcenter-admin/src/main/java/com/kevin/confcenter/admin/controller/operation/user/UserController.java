package com.kevin.confcenter.admin.controller.operation.user;

import com.kevin.confcenter.admin.controller.BaseController;
import com.kevin.confcenter.admin.service.operation.user.UserService;
import com.kevin.confcenter.common.bean.po.operation.User;
import com.kevin.confcenter.common.bean.vo.ResultInfo;
import com.kevin.confcenter.common.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: kevin
 * @Description: 用户管理controller
 * @Date: Created In 2018/4/12 16:31
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ResultInfo list() {
        return ResultInfo.success(userService.getUserList());
    }

    /**
     * 跳转到登录页面
     *
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "/html/user/login.html";
    }

    /**
     * 登录
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo login(HttpServletRequest request, String userName, String password) {
        try {
            User user = userService.login(userName, password);
            setUserSession(request, user);
        } catch (BusinessException e) {
            return ResultInfo.errorMessage(e.getMessage());
        } catch (Exception e) {
            return ResultInfo.errorMessage("登录失败");
        }
        return ResultInfo.success();
    }

    /**
     * 退出登录
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo logout(HttpServletRequest request) {
        deleteSession(request);
        return ResultInfo.success();
    }
}
