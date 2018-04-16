package com.kevin.confcenter.common.bean.vo;

import java.io.Serializable;

/**
 * @Author: kevin
 * @Description: 用户信息session
 * @Date: Created In 2018/4/16 14:36
 */
public class UserSession implements Serializable {

    private static final long serialVersionUID = -4226091899824200078L;

    /**
     * id
     */
    private Long id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 账户类型，{@link com.kevin.confcenter.common.consts.web.operation.UserTypeEnum}
     */
    private Integer type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "UserSession{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", type=" + type +
                '}';
    }
}
