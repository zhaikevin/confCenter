package com.kevin.confcenter.admin.service.operation;

import com.kevin.confcenter.common.bean.po.operation.User;
import com.kevin.confcenter.common.bean.vo.PaginationResult;
import com.kevin.confcenter.common.bean.vo.QueryParams;

/**
 * @Author: kevin
 * @Description: 用户管理service
 * @Date: Created In 2018/4/12 17:36
 */
public interface UserService {

    /**
     * 获取分页列表
     *
     * @param queryParams
     * @return
     */
    PaginationResult<User> getPaginationList(QueryParams queryParams);

    /**
     * 登录
     *
     * @param userName 用户名
     * @param password 密码
     * @return
     */
    User login(String userName, String password);

    /**
     * 校验
     *
     * @param userName 用户名
     * @param password 密码
     */
    void validate(String userName, String password);

    /**
     * 创建用户
     *
     * @param user
     */
    Long create(User user);

    /**
     * 修改用户
     *
     * @param user
     */
    void modify(User user);

    /**
     * 用户详情
     *
     * @param id
     * @return
     */
    User detail(Long id);

    /**
     * 禁用用户
     *
     * @param id
     */
    void disable(Long id);

    /**
     * 启用用户
     *
     * @param id
     */
    void enable(Long id);
}
