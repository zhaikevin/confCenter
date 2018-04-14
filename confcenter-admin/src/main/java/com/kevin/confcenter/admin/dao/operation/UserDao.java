package com.kevin.confcenter.admin.dao.operation;

import com.kevin.confcenter.common.bean.po.operation.User;

import java.util.List;

/**
 * @Author: kevin
 * @Description: 用户dao
 * @Date: Created In 2018/4/13 15:01
 */
public interface UserDao {

    List<User> getUserList();

    /**
     * 根据名字获取信息
     *
     * @param name
     * @return
     */
    User getUserByName(String name);
}
