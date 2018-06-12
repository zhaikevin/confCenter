package com.kevin.confcenter.admin.extend;

import com.alibaba.fastjson.JSONObject;
import com.kevin.confcenter.common.bean.vo.UserCookie;
import com.kevin.confcenter.common.bean.vo.UserToken;
import com.kevin.confcenter.common.consts.Consts;
import com.kevin.confcenter.common.consts.web.operation.UserTypeEnum;
import com.kevin.confcenter.common.utils.DESUtil;
import org.apache.commons.lang.StringUtils;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @Author: kevin
 * @Description: 登录认证
 * @Date: Created In 2018/5/31 10:16
 */
public class AuthHelper {

    private AuthHelper() {

    }

    private static ThreadLocal<UserCookie> cookieThreadLocal = new ThreadLocal<>();

    /**
     * 获取用户信息
     *
     * @return
     */
    public static UserCookie getUserCookie() {
        return cookieThreadLocal.get();
    }

    public static Long getUserId() {
        UserCookie userCookie = getUserCookie();
        if (userCookie != null) {
            return userCookie.getId();
        }
        return null;
    }

    public void setUserCookie(UserCookie userCookie) {
        cookieThreadLocal.set(userCookie);
    }

    /**
     * 创建token
     *
     * @param userToken
     * @return
     */
    public static String createToken(UserToken userToken) throws Exception {
        if (userToken == null) {
            return null;
        }
        String clearToken = JSONObject.toJSONString(userToken);
        String token = DESUtil.encrypt(clearToken);
        return URLEncoder.encode(token, Consts.UTF8);
    }

    /**
     * 解析token
     *
     * @param token
     * @return
     */
    public static UserToken parseToken(String token) throws Exception {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        String clearToken = DESUtil.decrypt(URLDecoder.decode(token, Consts.UTF8));
        UserToken userToken = JSONObject.parseObject(clearToken, UserToken.class);
        return userToken;
    }

    public static void main(String args[]) throws Exception {
        UserToken userToken = new UserToken();
        userToken.setId(1L);
        userToken.setType(UserTypeEnum.ADMIN.getVal());
        userToken.setUserName("xuser");
        String token = createToken(userToken);
        System.out.println(token);
        System.out.println(parseToken(token));
    }
}
