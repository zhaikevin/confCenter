package com.kevin.confcenter.admin.service.operation.impl;

import com.kevin.confcenter.admin.dao.operation.UserDao;
import com.kevin.confcenter.admin.log.Log;
import com.kevin.confcenter.admin.log.handler.HandlerName;
import com.kevin.confcenter.admin.service.operation.UserService;
import com.kevin.confcenter.common.bean.po.operation.User;
import com.kevin.confcenter.common.bean.vo.PaginationResult;
import com.kevin.confcenter.common.bean.vo.QueryParams;
import com.kevin.confcenter.common.consts.Consts;
import com.kevin.confcenter.common.consts.web.CommonStatusEnum;
import com.kevin.confcenter.common.exception.BusinessException;
import com.kevin.confcenter.common.utils.Md5Util;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
    public PaginationResult<User> getPaginationList(QueryParams queryParams) {
        Map<String, Object> params = queryParams.getParams();
        List<User> list = userDao.getUserList(params);
        Long count = userDao.getUserCount(params);
        return new PaginationResult<>(count, list);
    }

    @Override
    public User login(String userName, String password) {
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
            throw new BusinessException("用户名或密码为空");
        }
        User user = userDao.getUserByName(userName);
        if (user == null) {
            throw new BusinessException("用户名不存在");
        }
        password = Md5Util.getMd5Code(password);
        if (!user.getPassword().equals(password)) {
            throw new BusinessException("用户名或密码错误");
        }
        if (new Integer(CommonStatusEnum.DISABLED.getVal()).equals(user.getStatus())) {
            throw new BusinessException("用户名无效");
        }
        return user;
    }

    @Override
    public void validate(String userName, String password) {
        if (StringUtils.isEmpty(password)) {
            throw new BusinessException("密码为空");
        }
        User user = userDao.getUserByName(userName);
        if (user == null) {
            throw new BusinessException("用户不存在，请联系管理员");
        }
        if (new Integer(CommonStatusEnum.DISABLED.getVal()).equals(user.getStatus())) {
            throw new BusinessException("用户被禁用，请联系管理员");
        }
        password = Md5Util.getMd5Code(password);
        if (!user.getPassword().equals(password)) {
            throw new BusinessException("密码错误");
        }
    }

    @Override
    public void create(User user) {
        User oldUser = userDao.getUserByName(user.getUserName());
        if (oldUser != null) {
            throw new BusinessException("用户名不能重复");
        }
        String password = Md5Util.getMd5Code(Consts.DEFAULT_PASSWORD);
        user.setPassword(password);
        userDao.insert(user);
    }

    @Override
    @Log(handler = HandlerName.USER_MODIFY_NAME)
    public void modify(User user) {
        User oldUser = userDao.getUserByName(user.getUserName());
        if (oldUser != null && !oldUser.getId().equals(user.getId())) {
            throw new BusinessException("用户名不能重复");
        }
        userDao.update(user);
    }

    @Override
    public User detail(Long id) {
        return userDao.getUserById(id);
    }

    @Override
    @Log(handler = HandlerName.USER_DISABLE_NAME)
    public void disable(Long id) {
        User user = userDao.getUserById(id);
        if (CommonStatusEnum.DISABLED.getVal().equals(user.getStatus())) {
            throw new BusinessException("该用户已经被禁用，不能再次被禁用");
        }
        User updateUser = new User();
        updateUser.setId(id);
        updateUser.setStatus(CommonStatusEnum.DISABLED.getVal());
        userDao.update(updateUser);
    }

    @Override
    @Log(handler = HandlerName.USER_ENABLE_NAME)
    public void enable(Long id) {
        User user = userDao.getUserById(id);
        if (CommonStatusEnum.ENABLED.getVal().equals(user.getStatus())) {
            throw new BusinessException("该用户已经被启用，不能再次被启用");
        }
        User updateUser = new User();
        updateUser.setId(id);
        updateUser.setStatus(CommonStatusEnum.ENABLED.getVal());
        userDao.update(updateUser);
    }
}
