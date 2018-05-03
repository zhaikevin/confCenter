package com.kevin.confcenter.common.consts;

/**
 * @Author: kevin
 * @Description: 常量
 * @Date: Created In 2018/3/17 17:50
 */
public class Consts {

    /**
     * 应答结果状态码——成功
     */
    public static final int RESULT_CODE_SUCCESS = 0;
    /**
     * 应答结果状态码——通用错误
     */
    public static final int RESULT_CODE_COMMONERR = 9999;

    /**
     * session过期
     */
    public static final int RESULT_SESSION_TIMEOUT = 1;

    public static final String PING_PARAM_IP = "ip";
    public static final String PING_PARAM_PROJECT = "projectName";
    public static final String PING_SERVER_URL = "/confcenter-admin/ping";

}
