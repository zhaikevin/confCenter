package com.kevin.confcenter.admin.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author: jizhong.zhai
 * @Description: 所有属性
 * @Date: Created In 2018/4/10 16:09
 */
@Component
public class Properties {

    /**
     * ZK根目录
     */
    @Value("${zookeeper.zkRoot}")
    private String zkRoot;

    /**
     * ZK集群host
     */
    @Value("${zookeeper.zkConnet}")
    private String zkConnet;

    /**
     * ZK 连接超时时间
     */
    @Value("${zookeeper.zkSessionTimeoutMs}")
    private int zkSessionTimeoutMs;

    /**
     * 最大等待连接时间
     */
    @Value("${zookeeper.zkConnectionTimeoutMs}")
    private int zkConnectionTimeoutMs;

    public String getZkRoot() {
        return zkRoot;
    }

    public void setZkRoot(String zkRoot) {
        this.zkRoot = zkRoot;
    }

    public String getZkConnet() {
        return zkConnet;
    }

    public void setZkConnet(String zkConnet) {
        this.zkConnet = zkConnet;
    }

    public int getZkSessionTimeoutMs() {
        return zkSessionTimeoutMs;
    }

    public void setZkSessionTimeoutMs(int zkSessionTimeoutMs) {
        this.zkSessionTimeoutMs = zkSessionTimeoutMs;
    }

    public int getZkConnectionTimeoutMs() {
        return zkConnectionTimeoutMs;
    }

    public void setZkConnectionTimeoutMs(int zkConnectionTimeoutMs) {
        this.zkConnectionTimeoutMs = zkConnectionTimeoutMs;
    }

    @Override
    public String toString() {
        return "Properties{" +
                "zkRoot='" + zkRoot + '\'' +
                ", zkConnet='" + zkConnet + '\'' +
                ", zkSessionTimeoutMs=" + zkSessionTimeoutMs +
                ", zkConnectionTimeoutMs=" + zkConnectionTimeoutMs +
                '}';
    }
}
