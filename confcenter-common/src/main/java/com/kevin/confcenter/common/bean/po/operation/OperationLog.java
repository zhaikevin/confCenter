package com.kevin.confcenter.common.bean.po.operation;

import java.io.Serializable;
import java.util.Date;

/**
 * 操作记录,对应表：conf_center_operation_log
 */
public class OperationLog implements Serializable {


    private static final long serialVersionUID = 7812942865028909991L;

    /**
     * 主键id,对应数据表中的字段:conf_center_operation_log.id
     */
    private Long id;

    /**
     * 用户id,对应数据表中的字段:conf_center_operation_log.user_id
     */
    private Long userId;

    /**
     * 操作类型,对应数据表中的字段:conf_center_operation_log.type
     */
    private Byte type;

    /**
     * 操作对象类型‹,对应数据表中的字段:conf_center_operation_log.target_type
     */
    private Byte targetType;

    /**
     * 操作对象数据id,对应数据表中的字段:conf_center_operation_log.target_id
     */
    private Long targetId;

    /**
     * 操作对象数据名称
     */
    private String targetName;

    /**
     * 执行结果,对应数据表中的字段:conf_center_operation_log.result
     */
    private Byte result;

    /**
     * 操作备注,对应数据表中的字段:conf_center_operation_log.remark
     */
    private String remark;

    /**
     * 错误信息,对应数据表中的字段:conf_center_operation_log.error_msg
     */
    private String errorMsg;

    /**
     * 操作时间,对应数据表中的字段:conf_center_operation_log.create_time
     */
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Byte getTargetType() {
        return targetType;
    }

    public void setTargetType(Byte targetType) {
        this.targetType = targetType;
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public Byte getResult() {
        return result;
    }

    public void setResult(Byte result) {
        this.result = result;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg == null ? null : errorMsg.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    @Override
    public String toString() {
        return "OperationLog{" +
                "id=" + id +
                ", userId=" + userId +
                ", type=" + type +
                ", targetType=" + targetType +
                ", targetId=" + targetId +
                ", targetName='" + targetName + '\'' +
                ", result=" + result +
                ", remark='" + remark + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}