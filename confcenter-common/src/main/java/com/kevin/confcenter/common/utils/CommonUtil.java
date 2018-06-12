package com.kevin.confcenter.common.utils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @Author: kevin
 * @Description: 通用方法工具类
 * @Date: Created In 2018/3/15 11:41
 */
public class CommonUtil {

    private final static Logger LOGGER = LoggerFactory.getLogger(CommonUtil.class);

    private CommonUtil() {

    }

    /**
     * 是否空map
     *
     * @param map
     * @return
     */
    public static Boolean isEmptyMap(Map map) {
        return (map == null || map.isEmpty());
    }

    /**
     * 首字母大写
     *
     * @param str
     * @return
     */
    public static String upperFirst(String str) {
        if (StringUtils.isEmpty(str)) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
