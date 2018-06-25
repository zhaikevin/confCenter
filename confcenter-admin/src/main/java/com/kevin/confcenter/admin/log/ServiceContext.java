package com.kevin.confcenter.admin.log;

import java.lang.annotation.Annotation;
import java.util.Arrays;

public class ServiceContext {

    private Object[] params;

    private Annotation[] annotations;

    private Object attribute;

    private Boolean result;

    private String message;

    private Long userId;

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

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
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

    @Override
    public String toString() {
        return "ServiceContext{" +
                "params=" + Arrays.toString(params) +
                ", annotations=" + Arrays.toString(annotations) +
                ", attribute=" + attribute +
                ", result=" + result +
                ", message='" + message + '\'' +
                ", userId=" + userId +
                '}';
    }
}
