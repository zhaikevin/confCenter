package com.kevin.confcenter.common.utils;

import com.kevin.confcenter.common.bean.vo.ClientDataSource;
import com.kevin.confcenter.common.bean.vo.ZKConfig;
import com.kevin.confcenter.common.consts.SourceTypeEnum;
import org.I0Itec.zkclient.ZkClient;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * ZK客户端
     */
    private ZkClient zkClient;

    public String getZkRoot() {
        return zkRoot;
    }

    public ZKConfig getZkConfig() {
        return zkConfig;
    }

    public ZkClient getZkClient() {
        return zkClient;
    }

    public String getNormalDataSourcePath() {
        return normalDataSourcePath;
    }

    public String getPublicDataSourcePath() {
        return publicDataSourcePath;
    }

    public ConfCenterZookeeper(ZKConfig zkConfig, String zkRoot, String projectName, ZkClient zkClient) {
        this.zkConfig = zkConfig;
        this.zkClient = zkClient;
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

    /**
     * 获取所有的数据源
     * @param sourceType 数据源类型
     * @return
     */
    public List<ClientDataSource> getAllDataSource(Integer sourceType) {
        String path = this.normalDataSourcePath;
        if (sourceType.equals(SourceTypeEnum.PUBLIC.getVal())) {
            path = this.publicDataSourcePath;
        }
        List<ClientDataSource> dataSourceList = new ArrayList<>();
        List<String> sourceKeys = ZkUtil.getChildrenMaybeNull(this.zkClient, path);
        if (CollectionUtils.isEmpty(sourceKeys)) {
            return dataSourceList;
        }
        for (String sourceKey : sourceKeys) {
            String sourcePath = path + "/" + sourceKey;
            String sourceValue = ZkUtil.readData(this.zkClient, sourcePath);
            ClientDataSource dataSource = new ClientDataSource();
            dataSource.setSourceKey(sourceKey);
            dataSource.setSourceType(sourceType);
            dataSource.setSourceValue(sourceValue);
            dataSourceList.add(dataSource);
        }
        return dataSourceList;
    }
}
