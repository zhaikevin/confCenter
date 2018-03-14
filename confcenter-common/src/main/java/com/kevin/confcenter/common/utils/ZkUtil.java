package com.kevin.confcenter.common.utils;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkMarshallingError;
import org.I0Itec.zkclient.exception.ZkNoNodeException;
import org.I0Itec.zkclient.serialize.ZkSerializer;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @Author: kevin
 * @Description: zk util
 * @Date: Created In 2018/3/9 8:39
 */
public class ZkUtil {

    public static class StringSerializer implements ZkSerializer {

        @Override
        public Object deserialize(final byte[] bytes) throws ZkMarshallingError {
            try {
                return new String(bytes, "utf-8");
            } catch (final UnsupportedEncodingException e) {
                throw new ZkMarshallingError(e);
            }
        }


        @Override
        public byte[] serialize(final Object data) throws ZkMarshallingError {
            try {
                return ((String) data).getBytes("utf-8");
            } catch (final UnsupportedEncodingException e) {
                throw new ZkMarshallingError(e);
            }
        }

    }

    /**
     * 获取子节点
     * @param client
     * @param path
     * @return
     */
    public static List<String> getChildren(final ZkClient client, final String path) {
        return client.getChildren(path);
    }


    /**
     * 获取子节点，可能为null
     * @param client
     * @param path
     * @return
     */
    public static List<String> getChildrenMaybeNull(final ZkClient client, final String path) {
        try {
            return client.getChildren(path);
        }
        catch (final ZkNoNodeException e) {
            return null;
        }
    }

    /**
     * 读取数据
     * @param client
     * @param path
     * @return
     */
    public static String readData(final ZkClient client, final String path) {
        return client.readData(path);
    }

    /**
     * 读取数据，可能为null
     * @param client
     * @param path
     * @return
     */
    public static String readDataMaybeNull(final ZkClient client, final String path) {
        return client.readData(path, true);
    }
}
