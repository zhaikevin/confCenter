
package com.kevin.confcenter.common.exception;

/**
 * @Author: kevin
 * @Description: 业务异常.
 * @Date: Created In 2018/3/10 14:51
 */
public class BusinessException extends ConfCenterException {


    private static final long serialVersionUID = 7909749112523340379L;

    /**
     * 默认构造函数
     */
    public BusinessException() {
        super("business exception");
    }

    /**
     * 构造函数
     *
     * @param errMsg 异常消息
     */
    public BusinessException(String errMsg) {
        super(errMsg);
    }

    /**
     * 构造函数
     *
     * @param cause 原始异常
     */
    public BusinessException(Throwable cause) {
        super(cause);
    }
}
