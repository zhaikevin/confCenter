package com.kevin.confcenter.common.bean.vo;

/**
 * @Author: kevin
 * @Description: ZK配置
 * @Date: Created In 2018/3/6 8:30
 */
public class ZKConfig {

    /**
     * ZK根目录
     */
    private String zkRoot = "/conf";

    /**
     * ZK集群host
     */
    private String zkConnet;

    /**
     * ZK 连接超时时间
     */
    private int zkSessionTimeoutMs = 30000;

    /**
     * 最大等待连接时间
     */
    private int zkConnectionTimeoutMs = 30000;


    public ZKConfig(String zkConnet) {
        this.zkConnet = zkConnet;
    }

    public ZKConfig(String zkRoot, String zkConnet, int zkSessionTimeoutMs,
                    int zkConnectionTimeoutMs) {
        this.zkRoot = zkRoot;
        this.zkConnet = zkConnet;
        this.zkSessionTimeoutMs = zkSessionTimeoutMs;
        this.zkConnectionTimeoutMs = zkConnectionTimeoutMs;
    }

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
        return "ZKConfig{" +
                "zkRoot='" + zkRoot + '\'' +
                ", zkConnet='" + zkConnet + '\'' +
                ", zkSessionTimeoutMs=" + zkSessionTimeoutMs +
                ", zkConnectionTimeoutMs=" + zkConnectionTimeoutMs +
                '}';
    }
}
