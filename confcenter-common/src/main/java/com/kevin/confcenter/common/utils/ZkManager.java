package com.kevin.confcenter.common.utils;

import org.apache.zookeeper.ZooKeeper;

/**
 * @Author: kevin
 * @Description: zk管理器
 * @Date: Created In 2018/3/9 8:39
 */
public class ZkManager {

    /**ZK地址**/
    //private final String zkConnet;
    /**连接超时时间**/
    //private final int timeoutMs;
    /**最大等待连接时间**/
    //private final int maxConnectionWaitTimeMs;
    /**间隔多长时间检查连接是否成功**/
    //private final int connectedCheckDelayMs;
    /**ZK对象**/
    private volatile ZooKeeper zk;



}
