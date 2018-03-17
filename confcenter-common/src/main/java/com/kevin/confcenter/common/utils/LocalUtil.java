
package com.kevin.confcenter.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * 本地资源工具类
 */
public final class LocalUtil {

    public static String LOCAL_IP = getLocalIp();

    private static final Logger LOGGER = LoggerFactory.getLogger(LocalUtil.class);

    private LocalUtil() {
    }

    /**
     * 获取本机ip地址
     * 此方法为重量级的方法，不要频繁调用
     *
     * @return
     */
    public static String getLocalIp() {
        if (LOCAL_IP != null) {
            return LOCAL_IP;
        }
        try {
            //根据网卡取本机配置的IP
            Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
            String ip = null;
            a:
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface ni = netInterfaces.nextElement();
                Enumeration<InetAddress> ips = ni.getInetAddresses();
                while (ips.hasMoreElements()) {
                    InetAddress ipObj = ips.nextElement();
                    if (ipObj.isSiteLocalAddress()) {
                        ip = ipObj.getHostAddress();
                        break a;
                    }
                }
            }
            return ip;
        } catch (Exception e) {
            LOGGER.error("获取本机ip异常", e);
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(getLocalIp());
    }

}
