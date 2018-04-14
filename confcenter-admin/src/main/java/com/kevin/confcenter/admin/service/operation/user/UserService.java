package com.kevin.confcenter.admin.service.operation.user;

import com.kevin.confcenter.common.bean.po.operation.User;

import java.util.List;

/**
 * @Author: kevin
 * @Description: 用户管理service
 * @Date: Created In 2018/4/12 17:36
 */
public interface UserService {

    List<User> getUserList();

    /**
     * 登录
     *
     * @param userName 用户名
     * @param password 密码
     * @return
     */
    User login(String userName, String password);
}
