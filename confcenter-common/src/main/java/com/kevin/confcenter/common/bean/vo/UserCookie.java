package com.kevin.confcenter.common.bean.vo;

import com.kevin.confcenter.common.bean.po.operation.User;

import java.io.Serializable;

/**
 * @Author: kevin
 * @Description: cookie存储的user信息
 * @Date: Created In 2018/5/31 13:59
 */
public class UserCookie implements Serializable {

    private static final long serialVersionUID = 3556005517481449381L;

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

    /**
     * token
     */
    private String token;

    public UserCookie() {

    }

    public UserCookie(User user,String token) {
        id = user.getId();
        userName = user.getUserName();
        type = user.getType();
        this.token = token;
    }

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "UserCookie{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", type=" + type +
                ", token='" + token + '\'' +
                '}';
    }
}
