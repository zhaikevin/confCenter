package com.kevin.confcenter.common.bean.po.operation;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: kevin
 * @Description:
 * @Date: Created In 2018/4/12 17:50
 */
public class User implements Serializable {

    private static final long serialVersionUID = 661083522776455304L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 账户类型，{@link com.kevin.confcenter.common.consts.web.operation.UserTypeEnum}
     */
    private Integer type;

    /**
     * 状态，1：有效，0：无效
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 邮箱
     */
    private String mail;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", type=" + type +
                ", status=" + status +
                ", createTime=" + createTime +
                ", mail='" + mail + '\'' +
                '}';
    }
}
