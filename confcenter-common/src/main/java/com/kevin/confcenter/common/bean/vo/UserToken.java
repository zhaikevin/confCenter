package com.kevin.confcenter.common.bean.vo;

import com.kevin.confcenter.common.utils.DateUtil;

import java.io.Serializable;
import java.util.UUID;

/**
 * @Author: kevin
 * @Description: 放在token中的用户信息
 * @Date: Created In 2018/4/16 14:36
 */
public class UserToken implements Serializable {

    private static final long serialVersionUID = -4226091899824200078L;

    /**
     * uuid
     */
    private String uuidStr;

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
     * 时间戳
     */
    private String date;

    public String getUuidStr() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 16);
    }

    public void setUuidStr(String uuidStr) {
        this.uuidStr = uuidStr;
    }

    public String getDate() {
        return DateUtil.dateToString(DateUtil.current());
    }

    public void setDate(String date) {
        this.date = date;
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

    @Override
    public String toString() {
        return "UserToken{" +
                "uuidStr='" + uuidStr + '\'' +
                ", id=" + id +
                ", userName='" + userName + '\'' +
                ", type=" + type +
                ", date='" + date + '\'' +
                '}';
    }
}
