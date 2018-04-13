package com.kevin.confcenter.admin.service.operation.user.impl;

import com.kevin.confcenter.admin.bean.operation.User;
import com.kevin.confcenter.admin.dao.operation.UserDao;
import com.kevin.confcenter.admin.service.operation.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: kevin
 * @Description: 会员管理service实现
 * @Date: Created In 2018/4/12 17:38
 */
@Service(value = "userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public List<User> getUserList() {
        return userDao.getUserList();
    }
}
