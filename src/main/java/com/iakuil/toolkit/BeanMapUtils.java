package com.iakuil.toolkit;

import net.sf.cglib.beans.BeanMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Java Bean与Map转换工具
 *
 * <p>封装了cglib的{@link BeanMap}</p>
 *
 * @author Kai
 */
public class BeanMapUtils {

    /**
     * 将map转换为javabean对象
     *
     * @param bean javabean对象
     * @return Map<String, Object>形式的Map
     */
    public static <T> Map<String, Object> beanToMap(T bean) {
        return beanToMap(bean, false);
    }

    /**
     * 将map转换为javabean对象
     *
     * @param bean       javabean对象
     * @param ignoreNull 是否忽略空置
     * @return Map<String, Object>形式的Map
     */
    public static <T> Map<String, Object> beanToMap(T bean, boolean ignoreNull) {
        Map<String, Object> map = new HashMap<>();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                Object value = beanMap.get(key);
                map.put(key + "", value);
            }
        }
        return map;
    }

    /**
     * 将map转换为javabean对象
     *
     * @param map  Map数据
     * @param bean javabean对象
     * @return 指定类型的javabean
     */
    public static <T> T mapToBean(Map<String, Object> map, T bean) {
        if (map != null) {
            BeanMap beanMap = BeanMap.create(bean);
            beanMap.putAll(map);
        }
        return bean;
    }

    /**
     * 将List<T>转换为List<Map<String, Object>>
     *
     * @param objList javabean对象列表
     * @return Map<String, Object>形式的Map列表
     */
    public static <T> List<Map<String, Object>> objectsToMaps(List<T> objList) {
        List<Map<String, Object>> list = new ArrayList<>();
        if (objList != null && objList.size() > 0) {
            Map<String, Object> map;
            T bean;
            for (T t : objList) {
                bean = t;
                map = beanToMap(bean);
                list.add(map);
            }
        }
        return list;
    }

    /**
     * 将List<Map<String,Object>>转换为List<T>
     *
     * @param maps  Map数据列表
     * @param clazz 期望的Java类型
     * @return javabean列表
     */
    public static <T> List<T> mapsToObjects(List<Map<String, Object>> maps, Class<T> clazz) {
        List<T> list = new ArrayList<>();
        if (maps != null && maps.size() > 0) {
            Map<String, Object> map;
            T bean;
            for (Map<String, Object> stringObjectMap : maps) {
                map = stringObjectMap;
                try {
                    bean = clazz.newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new IllegalStateException("Occurring an exception during class instancing!", e);
                }
                mapToBean(map, bean);
                list.add(bean);
            }
        }
        return list;
    }
}