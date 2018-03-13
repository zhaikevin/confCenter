package com.kevin.confcenter.common.utils;

import com.kevin.confcenter.common.bean.vo.ZKConfig;

/**
 * @Author: kevin
 * @Description: zk工具类
 * @Date: Created In 2018/3/8 8:11
 */
public class ConfCenterZookeeper {

    /**
     * ZK根节点
     */
    private String zkRoot;

    /**
     * ZK配置
     */
    private ZKConfig zkConfig;

    /**
     * 普通数据源路径
     */
    private String normalDataSourcePath;

    /**
     * 公共数据源路径
     */
    private String publicDataSourcePath;

    public String getZkRoot() {
        return zkRoot;
    }

    public ZKConfig getZkConfig() {
        return zkConfig;
    }

    public String getNormalDataSourcePath() {
        return normalDataSourcePath;
    }

    public String getPublicDataSourcePath() {
        return publicDataSourcePath;
    }

    public ConfCenterZookeeper(ZKConfig zkConfig, String zkRoot, String projectName) {
        this.zkConfig = zkConfig;
        this.zkRoot = this.normalize(zkRoot);
        this.normalDataSourcePath = this.zkRoot + "/" + projectName;
        this.publicDataSourcePath = this.zkRoot + "/public";
    }

    /**
     * 格式化
     *
     * @param root
     * @return
     */
    private String normalize(final String root) {
        if (root.startsWith("/")) {
            return this.removeLastSlash(root);
        } else {
            return "/" + this.removeLastSlash(root);
        }
    }

    /**
     * 去除结尾/
     *
     * @param root
     * @return
     */
    private String removeLastSlash(final String root) {
        if (root.endsWith("/")) {
            return root.substring(0, root.lastIndexOf("/"));
        } else {
            return root;
        }
    }
}
