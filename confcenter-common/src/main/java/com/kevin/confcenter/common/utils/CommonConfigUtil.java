package com.kevin.confcenter.common.utils;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: kevin
 * @Description: 配置文件工具类.
 * @Date: Created In 2018/3/10 14:51
 */
public class CommonConfigUtil {

    private final static Logger LOGGER = LoggerFactory.getLogger(CommonConfigUtil.class);

    /**
     * 配置内存缓存
     */
    private static Map<String, Configuration> configMap = new ConcurrentHashMap<String, Configuration>();

    /**
     * 构造函数
     *
     * @param
     * @return
     */
    private CommonConfigUtil() {
    }

    /**
     * 获取指定配置文件对应的configuration对象
     *
     * @param configFilename 配置文件
     * @return config 对象，如果配置文件不存在或者读取异常则返回null.
     */
    public static Configuration getConfig(String configFilename) {
        return getConfig(configFilename, CommonConfigUtil.class.getClassLoader());
    }

    /**
     * 通过指定的classloader获取配置文件对应的configuration对象
     *
     * @param configFilename 文件名称
     * @param classLoader    加载类
     * @return Configuration
     */
    public static Configuration getConfig(String configFilename, ClassLoader classLoader) {
        Configuration config = configMap.get(configFilename);
        if (config != null) {
            return config;
        }
        try {
            URL url = classLoader.getResource(configFilename);
            config = new PropertiesConfiguration(url);
            configMap.put(configFilename, config);
            return config;
        } catch (ConfigurationException e) {
            LOGGER.error("get config with " + configFilename + " failed.", e);
            return null;
        }
    }
}
