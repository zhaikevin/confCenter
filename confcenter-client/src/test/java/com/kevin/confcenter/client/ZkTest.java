package com.kevin.confcenter.client;

import com.kevin.confcenter.common.bean.vo.ZKConfig;
import com.kevin.confcenter.common.utils.ConfCenterZookeeper;
import com.kevin.confcenter.common.utils.ZkUtil;
import org.I0Itec.zkclient.ZkClient;
import org.junit.Before;
import org.junit.Test;

/**
 * @Author: kevin
 * @Description:
 * @Date: Created In 2018/3/19 10:17
 */
public class ZkTest {

    private ZkClient zkClient;

    private String parentPath;

    @Before
    public void init() {
        ZKConfig zkConfig = new ZKConfig("127.0.0.1:2181");
        zkClient = new ZkClient(zkConfig.getZkConnet(), zkConfig.getZkSessionTimeoutMs(),
                zkConfig.getZkConnectionTimeoutMs(), new ZkUtil.StringSerializer());
        ConfCenterZookeeper confCenterZookeeper = new ConfCenterZookeeper(zkConfig, zkConfig.getZkRoot(),
                "test", zkClient);
        parentPath = confCenterZookeeper.getNormalDataSourcePath();
    }

    @Test
    public void createNodeTest() throws Exception {
        ZkUtil.createParentPath(zkClient, parentPath);
        ZkUtil.writeData(zkClient, parentPath + "/testKey2", "hello2");
        System.out.println(ZkUtil.readData(zkClient, parentPath + "/testKey"));
    }

    @Test
    public void updateDataTest() throws Exception {
        ZkUtil.writeData(zkClient, parentPath + "/testKey", "hello");
        System.out.println(ZkUtil.readData(zkClient, parentPath + "/testKey"));
    }

    @Test
    public void deleteNodeTest() throws Exception {
        ZkUtil.deletePath(zkClient,parentPath + "/testKey2");
    }
}
