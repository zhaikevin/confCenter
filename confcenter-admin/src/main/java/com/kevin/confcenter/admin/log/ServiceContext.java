package com.kevin.confcenter.admin.log;

import java.lang.annotation.Annotation;
import java.util.Arrays;

public class ServiceContext {

    /**
     * 参数，aop方法的入参
     */
    private Object[] params;

    /**
     * 注解，aop方法上的注解
     */
    private Annotation[] annotations;

    /**
     * 属性，before方法获取的
     */
    private Object attribute;

    /**
     * 结果是否成功
     */
    private Boolean isSuccess;

    /**
     * 异常信息
     */
    private String message;

    /**
     * 操作人id
     */
    private Long userId;

    /**
     * 执行结果
     */
    private Object result;

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    public Annotation[] getAnnotations() {
        return annotations;
    }

    public void setAnnotations(Annotation[] annotations) {
        this.annotations = annotations;
    }

    public Object getAttribute() {
        return attribute;
    }

    public void setAttribute(Object attribute) {
        this.attribute = attribute;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "ServiceContext{" +
                "params=" + Arrays.toString(params) +
                ", annotations=" + Arrays.toString(annotations) +
                ", attribute=" + attribute +
                ", isSuccess=" + isSuccess +
                ", message='" + message + '\'' +
                ", userId=" + userId +
                ", result=" + result +
                '}';
    }
}
