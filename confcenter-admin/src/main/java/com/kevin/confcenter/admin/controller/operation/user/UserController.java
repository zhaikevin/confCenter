package com.kevin.confcenter.admin.controller.operation.user;

import com.kevin.confcenter.admin.extend.AuthHelper;
import com.kevin.confcenter.admin.service.operation.user.UserService;
import com.kevin.confcenter.common.bean.po.operation.User;
import com.kevin.confcenter.common.bean.vo.PaginationResult;
import com.kevin.confcenter.common.bean.vo.QueryParams;
import com.kevin.confcenter.common.bean.vo.ResultInfo;
import com.kevin.confcenter.common.bean.vo.UserCookie;
import com.kevin.confcenter.common.bean.vo.UserToken;
import com.kevin.confcenter.common.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: kevin
 * @Description: 用户管理controller
 * @Date: Created In 2018/4/12 16:31
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * 登录
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResultInfo login(HttpServletRequest request, HttpServletResponse response, String userName, String password) {
        try {
            User user = userService.login(userName, password);
            String token = AuthHelper.createToken(new UserToken(user));
            UserCookie cookie = new UserCookie(user, token);
            return ResultInfo.success(cookie);
        } catch (BusinessException e) {
            return ResultInfo.errorMessage(e.getMessage());
        } catch (Exception e) {
            LOGGER.error("login failed:{}", e.getMessage(), e);
            return ResultInfo.errorMessage("登录失败");
        }
    }

    /**
     * 获取列表
     *
     * @param queryParams
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
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
    public ResultInfo detail(Long id) {
        return ResultInfo.success(userService.detail(id));
    }

    /**
     * 禁用
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/disable", method = RequestMethod.POST)
    public ResultInfo disable(Long id) {
        try {
            userService.disable(id);
            return ResultInfo.success();
        } catch (BusinessException e) {
            return ResultInfo.errorMessage(e.getMessage());
        }
    }

    /**
     * 启用
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/enable", method = RequestMethod.POST)
    public ResultInfo enable(Long id) {
        try {
            userService.enable(id);
            return ResultInfo.success();
        } catch (BusinessException e) {
            return ResultInfo.errorMessage(e.getMessage());
        }
    }
}
