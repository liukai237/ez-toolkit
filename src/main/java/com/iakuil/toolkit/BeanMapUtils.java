package com.iakuil.toolkit;

import net.sf.cglib.beans.BeanMap;

import java.util.*;

/**
 * The utility for converting between JavaBean and Map.
 *
 * <p>Base on {@link BeanMap} from cglib.
 *
 * @author Kai
 */
public class BeanMapUtils {
    private BeanMapUtils() {
    }

    /**
     * 将Map转换为JavaBean对象
     *
     * @param <T>  Map类型
     * @param bean JavaBean对象
     * @return a Map对象
     */
    public static <T> Map<String, Object> beanToMap(T bean) {
        return beanToMap(bean, false);
    }

    /**
     * 将Map转换为JavaBean对象
     *
     * @param <T>        Map类型
     * @param bean       JavaBean对象
     * @param ignoreNull 是否忽略空值，默认false
     * @return Map对象
     */
    public static <T> Map<String, Object> beanToMap(T bean, boolean ignoreNull) {
        if (bean == null) {
            return Collections.emptyMap();
        }

        Map<String, Object> map = new HashMap<>();
        BeanMap beanMap = BeanMap.create(bean);
        for (Object key : beanMap.keySet()) {
            Object value = beanMap.get(key);
            if (value == null && ignoreNull) {
                continue;
            }
            map.put(key + "", value);
        }
        return map;
    }

    /**
     * 将Map转换为JavaBean对象
     *
     * @param <T>   JavaBean类型
     * @param map   Map数据
     * @param clazz 目标类型
     * @return 指定类型的JavaBean
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
     * 将Map转换为JavaBean对象
     *
     * @param <T>  JavaBean类型
     * @param map  Map数据
     * @param bean JavaBean对象
     * @return 指定类型的JavaBean
     */
    public static <T> T mapToBean(Map<String, Object> map, T bean) {
        if (map != null) {
            BeanMap beanMap = BeanMap.create(bean);
            beanMap.putAll(map);
        }
        return bean;
    }

    /**
     * 将JavaBean列表转换为map列表
     *
     * @param <T>     JavaBean类型
     * @param objList JavaBean对象列表
     * @return Map列表
     */
    public static <T> List<Map<String, Object>> objectsToMaps(List<T> objList) {
        if (objList == null || objList.size() < 1) {
            return Collections.emptyList();
        }

        List<Map<String, Object>> list = new ArrayList<>();
        for (T obj : objList) {
            list.add(beanToMap(obj));
        }
        return list;
    }

    /**
     * 将Map列表转换为JavaBean列表
     *
     * @param <T>   JavaBean类型
     * @param maps  Map数据列表
     * @param clazz 期望的Java类型
     * @return JavaBean列表
     */
    public static <T> List<T> mapsToObjects(List<Map<String, Object>> maps, Class<T> clazz) {
        if (maps == null || maps.size() < 1) {
            return Collections.emptyList();
        }

        List<T> list = new ArrayList<>();
        for (Map<String, Object> objMap : maps) {
            list.add(mapToBean(objMap, clazz));
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