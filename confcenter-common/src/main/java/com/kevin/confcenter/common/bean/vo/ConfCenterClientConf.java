package com.kevin.confcenter.common.bean.vo;

/**
 * @Author: kevin
 * @Description: 客户端配置
 * @Date: Created In 2018/3/6 8:29
 */
public class ConfCenterClientConf {

    /**
     * ZK 配置
     */
    private ZKConfig zkConfig;

    /**
     * confcenter后台服务器地址
     */
    private String serverUrl;

    /**
     * 心跳同步时间
     */
    private int heartBeatTimeMs = 60000;

    /**
     * confcenter中的项目名称
     */
    private String projectName;

    /**
     * 线程池大小
     */
    private Integer poolSize = 5;

    public ConfCenterClientConf(ZKConfig zkConfig, String serverUrl, String projectName) {
        this.zkConfig = zkConfig;
        this.serverUrl = serverUrl;
        this.projectName = projectName;
    }

    public ConfCenterClientConf(ZKConfig zkConfig, String serverUrl, int heartBeatTimeMs, String projectName, Integer poolSize) {
        this.zkConfig = zkConfig;
        this.serverUrl = serverUrl;
        this.heartBeatTimeMs = heartBeatTimeMs;
        this.projectName = projectName;
        this.poolSize = poolSize;
    }

    public ZKConfig getZkConfig() {
        return zkConfig;
    }

    public void setZkConfig(ZKConfig zkConfig) {
        this.zkConfig = zkConfig;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public int getHeartBeatTimeMs() {
        return heartBeatTimeMs;
    }

    public void setHeartBeatTimeMs(int heartBeatTimeMs) {
        this.heartBeatTimeMs = heartBeatTimeMs;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getPoolSize() {
        return poolSize;
    }

    public void setPoolSize(Integer poolSize) {
        this.poolSize = poolSize;
    }

    @Override
    public String toString() {
        return "ConfCenterClientConf{" +
                "zkConfig=" + zkConfig +
                ", serverUrl='" + serverUrl + '\'' +
                ", heartBeatTimeMs=" + heartBeatTimeMs +
                ", projectName='" + projectName + '\'' +
                ", poolSize=" + poolSize +
                '}';
    }
}
