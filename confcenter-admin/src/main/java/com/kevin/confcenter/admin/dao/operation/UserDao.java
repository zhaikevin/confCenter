package com.kevin.confcenter.admin.dao.operation;

import com.kevin.confcenter.common.bean.po.operation.User;

import java.util.List;
import java.util.Map;

/**
 * @Author: kevin
 * @Description: 用户dao
 * @Date: Created In 2018/4/13 15:01
 */
public interface UserDao {

    /**
     * 获取分页列表
     *
     * @return
     */
    List<User> getUserList(Map<String, Object> params);

    /**
     * 获取总数，用于分页
     *
     * @param params
     * @return
     */
    Long getUserCount(Map<String, Object> params);

    /**
     * 根据名字获取信息
     *
     * @param name
     * @return
     */
    User getUserByName(String name);

    /**
     * 插入用户
     *
     * @param user
     */
    void insert(User user);

    /**
     * 更新用户
     *
     * @param user
     */
    void update(User user);

    /**
     * 根据id获取信息
     *
     * @param id
     * @return
     */
    User getUserById(Long id);
}
