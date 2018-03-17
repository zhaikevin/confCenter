package com.kevin.confcenter.common.exception;

/**
 * 远程调用异常.
 */
public class RemoteCallException extends ConfCenterException {
    /**
     * uid
     */
    private static final long serialVersionUID = -1368052202200712460L;

    /**
     * 构造函数
     */
    public RemoteCallException() {
        super("Remote Call Exception Occurd");
    }

    /**
     * 构造函数
     *
     * @param errMsg 异常消息
     */
    public RemoteCallException(String errMsg) {
        super(errMsg);
    }

    /**
     * 构造函数
     *
     * @param cause 原始exception
     */
    public RemoteCallException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造函数
     *
     * @param errMsg 异常消息
     * @param cause  原始异常
     */
    public RemoteCallException(String errMsg, Throwable cause) {
        super(errMsg, cause);
    }
}
