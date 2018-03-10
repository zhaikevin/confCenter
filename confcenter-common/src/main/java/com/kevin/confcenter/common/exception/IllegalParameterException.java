
package com.kevin.confcenter.common.exception;

/**
 * 非法传参异常.
 * @title IllegalParameterException
 * @description 非法传参异常. 
 * @author kevin
 * @date 2016年10月19日
 * @version 1.0
 */
public class IllegalParameterException extends ConfCenterException {
    /**
     * uid
     */
    private static final long serialVersionUID = -6277013304097552489L;

    /**
     * 默认构造函数
     */
    public IllegalParameterException() {
        super("Illegal Parameter");
    }

    /**
     * 构造函数
     * @param errMsg 异常消息
     */
    public IllegalParameterException(String errMsg) {
        super(errMsg);
    }

    /**
     * 构造函数
     * @param cause 原始异常
     */
    public IllegalParameterException(Throwable cause) {
        super(cause);
    }
}
