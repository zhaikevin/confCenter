
package com.kevin.confcenter.common.exception;

/**
 * @Author: kevin
 * @Description: 非法传参异常.
 * @Date: Created In 2018/3/10 14:51
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
