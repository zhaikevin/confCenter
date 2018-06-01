package com.kevin.confcenter.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: kevin
 * @Description: 日期工具类
 * @Date: Created In 2018/5/31 10:43
 */
public class DateUtil {

    private DateUtil() {

    }

    /**
     * 返回当前date
     * @return 当前日期对象
     */
    public static Date current() {
        return new Date();
    }

    /**
     * 日期转字符串
     * @param date 日期
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String dateToString(Date date) {
        if (date != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return dateFormat.format(date);
        }
        return null;
    }

    /**
     * 将指定日期格式化为简单日期格式,
     * 即yyyy-MM-dd.
     * @param date 待格式化的date对象，可以为null
     * @return 格式化后的日期，可能为null.
     */
    public static String formatSimpleDate(Date date) {
        if (date != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.format(date);
        }
        return null;
    }

    /**
     * 日期按照指定格式转字符串
     * @param date 日期
     * @param pattern 格式
     * @return 指定格式日期字符串
     */
    public static String dateToString(Date date, String pattern) {
        if (date != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            return dateFormat.format(date);
        }
        return null;
    }

}
