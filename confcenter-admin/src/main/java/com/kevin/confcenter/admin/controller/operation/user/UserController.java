package com.kevin.confcenter.admin.controller.operation.user;

import com.kevin.confcenter.admin.controller.BaseController;
import com.kevin.confcenter.admin.extend.AuthHelper;
import com.kevin.confcenter.admin.service.operation.user.UserService;
import com.kevin.confcenter.common.bean.po.operation.User;
import com.kevin.confcenter.common.bean.vo.PaginationResult;
import com.kevin.confcenter.common.bean.vo.QueryParams;
import com.kevin.confcenter.common.bean.vo.ResultInfo;
import com.kevin.confcenter.common.bean.vo.UserCookie;
import com.kevin.confcenter.common.bean.vo.UserToken;
import com.kevin.confcenter.common.exception.BusinessException;
import com.kevin.confcenter.common.exception.SessionTimeOutException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    /**
     * 跳转到登录页面
     *
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "user/login";
    }

    /**
     * 登录
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo login(HttpServletRequest request, HttpServletResponse response, String userName, String password) {
        try {
            User user = userService.login(userName, password);
            String token = AuthHelper.createToken(new UserToken(user));
            UserCookie cookie = new UserCookie(user, token);
            setUserSession(request, user);
            return ResultInfo.success(cookie);
        } catch (BusinessException e) {
            return ResultInfo.errorMessage(e.getMessage());
        } catch (Exception e) {
            return ResultInfo.errorMessage("登录失败");
        }
    }

    /**
     * 退出登录
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request) {
        deleteSession(request);
        return "/user/login";
    }

    /**
     * 获取当前用户信息，从session拿就行了
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/session", method = RequestMethod.GET)
    @ResponseBody
    public ResultInfo session(HttpServletRequest request) {
        try {
            UserToken userToken = getUserSession(request);
            return ResultInfo.success(userToken);
        } catch (SessionTimeOutException e) {
            return ResultInfo.sessionTimeout();
        }
    }

    /**
     * 跳转到列表
     *
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list() {
        return "user/list";
    }

    /**
     * 获取列表
     *
     * @param queryParams
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo list(QueryParams queryParams) {
        PaginationResult<User> result = userService.getPaginationList(queryParams);
        return ResultInfo.success(result);
    }

    /**
     * 创建新用户
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo create(User user) {
        try {
            userService.create(user);
            return ResultInfo.success();
        } catch (BusinessException e) {
            return ResultInfo.errorMessage(e.getMessage());
        }
    }

    /**
     * 修改用户
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo modify(User user) {
        try {
            userService.modify(user);
            return ResultInfo.success();
        } catch (BusinessException e) {
            return ResultInfo.errorMessage(e.getMessage());
        }
    }

    /**
     * 用户详情
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ResponseBody
    public ResultInfo detail(Long id) {
        return ResultInfo.success(userService.detail(id));
    }

    /**
     * 禁用
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/forbidden", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo forbidden(Long id) {
        try {
            userService.forbidden(id);
            return ResultInfo.success();
        } catch (BusinessException e) {
            return ResultInfo.errorMessage(e.getMessage());
        }
    }
}
