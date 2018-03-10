package com.kevin.confcenter.common.utils;

import org.I0Itec.zkclient.exception.ZkMarshallingError;
import org.I0Itec.zkclient.serialize.ZkSerializer;
import org.apache.zookeeper.ZooKeeper;

import java.io.UnsupportedEncodingException;

/**
 * @Author: kevin
 * @Description: zk管理器
 * @Date: Created In 2018/3/9 8:39
 */
public class ZkUtil {




    public static class StringSerializer implements ZkSerializer {

        @Override
        public Object deserialize(final byte[] bytes) throws ZkMarshallingError {
            try {
                return new String(bytes, "utf-8");
            }
            catch (final UnsupportedEncodingException e) {
                throw new ZkMarshallingError(e);
            }
        }


        @Override
        public byte[] serialize(final Object data) throws ZkMarshallingError {
            try {
                return ((String) data).getBytes("utf-8");
            }
            catch (final UnsupportedEncodingException e) {
                throw new ZkMarshallingError(e);
            }
        }

    }
}
