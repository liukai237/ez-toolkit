package com.iakuil.toolkit;

import net.sf.cglib.beans.BeanMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The utility for converting between Java Bean and Map.
 *
 * <p>Base on {@link BeanMap} from cglib.
 *
 * @author Kai
 */
public class BeanMapUtils {

    /**
     * 将map转换为javabean对象
     *
     * @param <T>  Map类型
     * @param bean javabean对象
     * @return a Map对象
     */
    public static <T> Map<String, Object> beanToMap(T bean) {
        return beanToMap(bean, false);
    }

    /**
     * 将map转换为javabean对象
     *
     * @param <T>        Map类型
     * @param bean       javabean对象
     * @param ignoreNull 是否忽略空值，默认false
     * @return Map对象
     */
    public static <T> Map<String, Object> beanToMap(T bean, boolean ignoreNull) {
        Map<String, Object> map = new HashMap<>();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                Object value = beanMap.get(key);
                if (value == null && ignoreNull) {
                    continue;
                }
                map.put(key + "", value);
            }
        }
        return map;
    }

    /**
     * 将map转换为javabean对象
     *
     * @param <T>   javabean类型
     * @param map   Map数据
     * @param clazz 目标类型
     * @return 指定类型的javabean
     */
    public static <T> T mapToBean(Map<String, Object> map, Class<T> clazz) {
        T target = getInstance(clazz);
        if (map != null) {
            BeanMap beanMap = BeanMap.create(target);
            beanMap.putAll(map);
        }
        return target;
    }

    /**
     * 将map转换为javabean对象
     *
     * @param <T>  javabean类型
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
     * 将javabean列表转换为map列表
     *
     * @param <T>     javabean类型
     * @param objList javabean对象列表
     * @return Map列表
     */
    public static <T> List<Map<String, Object>> objectsToMaps(List<T> objList) {
        List<Map<String, Object>> list = new ArrayList<>();
        if (objList != null && objList.size() > 0) {
            for (T obj : objList) {
                list.add(beanToMap(obj));
            }
        }
        return list;
    }

    /**
     * 将map列表转换为javabean列表
     *
     * @param <T>   javabean类型
     * @param maps  Map数据列表
     * @param clazz 期望的Java类型
     * @return javabean列表
     */
    public static <T> List<T> mapsToObjects(List<Map<String, Object>> maps, Class<T> clazz) {
        List<T> list = new ArrayList<>();
        if (maps != null && maps.size() > 0) {
            for (Map<String, Object> objMap : maps) {
                list.add(mapToBean(objMap, clazz));
            }
        }
        return list;
    }

    private static <T> T getInstance(Class<T> clazz) {
        T inst;
        try {
            inst = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalStateException("Occurring an exception during class instancing!", e);
        }

        return inst;
    }
}