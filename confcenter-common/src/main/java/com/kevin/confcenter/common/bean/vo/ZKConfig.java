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
    private int timeoutMs = 30000;

    /**
     * 最大等待连接时间
     */
    private int maxConnectionWaitTimeMs = 30000;

    /**
     * 间隔多长时间检查连接是否成功
     */
    private int connectedCheckDelayMs = 5000;


    public ZKConfig(String zkConnet) {
        this.zkConnet = zkConnet;
    }

    public ZKConfig(String zkRoot, String zkConnet, int timeoutMs, int maxConnectionWaitTimeMs,
                    int connectedCheckDelayMs) {
        this.zkRoot = zkRoot;
        this.zkConnet = zkConnet;
        this.timeoutMs = timeoutMs;
        this.maxConnectionWaitTimeMs = maxConnectionWaitTimeMs;
        this.connectedCheckDelayMs = connectedCheckDelayMs;
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

    public int getTimeoutMs() {
        return timeoutMs;
    }

    public void setTimeoutMs(int timeoutMs) {
        this.timeoutMs = timeoutMs;
    }

    public int getMaxConnectionWaitTimeMs() {
        return maxConnectionWaitTimeMs;
    }

    public void setMaxConnectionWaitTimeMs(int maxConnectionWaitTimeMs) {
        this.maxConnectionWaitTimeMs = maxConnectionWaitTimeMs;
    }

    public int getConnectedCheckDelayMs() {
        return connectedCheckDelayMs;
    }

    public void setConnectedCheckDelayMs(int connectedCheckDelayMs) {
        this.connectedCheckDelayMs = connectedCheckDelayMs;
    }

    @Override
    public String toString() {
        return "ZKConfig{" +
                "zkRoot='" + zkRoot + '\'' +
                ", zkConnet='" + zkConnet + '\'' +
                ", timeoutMs=" + timeoutMs +
                ", maxConnectionWaitTimeMs=" + maxConnectionWaitTimeMs +
                ", connectedCheckDelayMs=" + connectedCheckDelayMs +
                '}';
    }
}
