package com.kevin.confcenter.admin.controller.operation;

import com.kevin.confcenter.admin.extend.AuthHelper;
import com.kevin.confcenter.admin.service.operation.UserService;
import com.kevin.confcenter.common.bean.po.operation.User;
import com.kevin.confcenter.common.bean.vo.PaginationResult;
import com.kevin.confcenter.common.bean.vo.QueryParams;
import com.kevin.confcenter.common.bean.vo.ResultInfo;
import com.kevin.confcenter.common.bean.vo.UserCookie;
import com.kevin.confcenter.common.bean.vo.UserToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    public ResultInfo login(String userName, String password) throws Exception {
        User user = userService.login(userName, password);
        String token = AuthHelper.createToken(new UserToken(user));
        UserCookie cookie = new UserCookie(user, token);
        return ResultInfo.success(cookie);
    }

    @RequestMapping(value = "/validate", method = RequestMethod.POST)
    public ResultInfo validate(String userName, String password) {
        userService.validate(userName, password);
        return ResultInfo.success();
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
        userService.create(user);
        return ResultInfo.success();
    }

    /**
     * 修改用户
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public ResultInfo modify(User user) {
        userService.modify(user);
        return ResultInfo.success();
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
        userService.disable(id);
        return ResultInfo.success();
    }

    /**
     * 启用
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/enable", method = RequestMethod.POST)
    public ResultInfo enable(Long id) {
        userService.enable(id);
        return ResultInfo.success();
    }
}
