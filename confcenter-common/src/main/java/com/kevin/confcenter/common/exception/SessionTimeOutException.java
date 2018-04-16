
package com.kevin.confcenter.common.exception;

/**
 * @Author: kevin
 * @Description: session过期异常.
 * @Date: Created In 2018/3/10 14:51
 */
public class SessionTimeOutException extends ConfCenterException {


    private static final long serialVersionUID = 1963225932635225150L;

    /**
     * 默认构造函数
     */
    public SessionTimeOutException() {
        super("session time out exception");
    }

    /**
     * 构造函数
     *
     * @param errMsg 异常消息
     */
    public SessionTimeOutException(String errMsg) {
        super(errMsg);
    }

    /**
     * 构造函数
     *
     * @param cause 原始异常
     */
    public SessionTimeOutException(Throwable cause) {
        super(cause);
    }
}
