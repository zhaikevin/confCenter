/**
 * ucarinc.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.kevin.confcenter.common.exception;

/**
 * 基础异常类
 * @title ConfCenterException
 * @description 基础异常类. 
 * @author kevin
 * @date 2016年10月19日
 * @version 1.0
 */
public abstract class ConfCenterException extends RuntimeException {
    /**
     * uid
     */
    private static final long serialVersionUID = 8037891447646609768L;

    /**
     * 默认构造函数
     */
    public ConfCenterException() {
    }

    /**
     * 构造函数
     * @param errMsg 异常消息
     */
    public ConfCenterException(String errMsg) {
        super(errMsg);
    }

    /**
     * 构造函数
     * @param cause 原始异常
     */
    public ConfCenterException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造函数
     * @param errMsg 异常消息
     * @param cause 原始异常
     */
    public ConfCenterException(String errMsg, Throwable cause) {
        super(errMsg, cause);
    }

}
