package com.kevin.confcenter.common.exception;

/**
 * @Author: jizhong.zhai
 * @Description: 认证失败异常
 * @Date: Created In 2018/6/13 13:51
 */
public class AuthFailedException extends ConfCenterException {

    private static final long serialVersionUID = 5158539965444976337L;

    /**
     * 默认构造函数
     */
    public AuthFailedException() {
        super("auth fail exception");
    }

    /**
     * 构造函数
     *
     * @param errMsg 异常消息
     */
    public AuthFailedException(String errMsg) {
        super(errMsg);
    }

    /**
     * 构造函数
     *
     * @param cause 原始异常
     */
    public AuthFailedException(Throwable cause) {
        super(cause);
    }
}
