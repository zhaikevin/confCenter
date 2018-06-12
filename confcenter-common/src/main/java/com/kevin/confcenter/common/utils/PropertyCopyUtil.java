package com.kevin.confcenter.common.utils;

import com.kevin.confcenter.common.bean.annotation.Property;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: jizhong.zhai
 * @Description: copy属性
 * @Date: Created In 2018/6/12 16:45
 */
public class PropertyCopyUtil {

    private PropertyCopyUtil() {

    }

    /**
     * 将map对象copy属性到类中
     * @param clazz
     * @param map
     * @param <T>
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws InvocationTargetException
     */
    public static <T> T copyPropertyFromMap(Class<T> clazz, Map<String, Object> map) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        T result = clazz.newInstance();
        Field[] fields = clazz.getDeclaredFields();
        if (fields == null || fields.length == 0) {
            return result;
        }
        Method[] methods = clazz.getMethods();
        Map<String, Method> methodMap = new HashMap<>();
        for (Method method : methods) {
            methodMap.put(method.getName(), method);
        }
        for (Field field : fields) {
            String name = field.getName();
            Property property = field.getAnnotation(Property.class);
            if (property != null) {
                if (!property.isCopy()) {
                    continue;
                }
                if (StringUtils.isNotEmpty(property.name())) {
                    name = property.name();
                }
            }
            if (!map.containsKey(name)) {
                continue;
            }
            Object value = map.get(name);
            String methodName = "set" + CommonUtil.upperFirst(name);
            Method method = methodMap.get(methodName);
            if (method == null) {
                continue;
            }
            method.invoke(result, value);
        }
        return result;
    }
}
