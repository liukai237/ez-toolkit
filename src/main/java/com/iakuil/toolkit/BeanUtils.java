package com.iakuil.toolkit;

import net.sf.cglib.beans.BeanCopier;
import org.apache.commons.collections4.map.MultiKeyMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The utility for javabean properties copying.
 *
 * <p>Base on {@link BeanCopier} from cglib
 * <p>效率仅次于Getter/Setter以及Mapstruct。
 * <p>注意：属性名称相同而类型不同的属性不会被拷贝。
 *
 * @author Kai
 */
public class BeanUtils {
    private static MultiKeyMap<Class<?>, BeanCopier> cache = new MultiKeyMap<>();

    /**
     * 对象属性复制
     *
     * @param <T>  javabean类型
     * @param from 源对象
     * @param to   目标类型
     * @return 目标类型对象
     */
    public static <T> T copy(Object from, Class<T> to) {
        if (from == null) {
            return null;
        }

        T toObj = getInstance(to);
        getCopier(from.getClass(), to).copy(from, toObj, null);
        return toObj;
    }

    /**
     * 批量对象属性复制
     *
     * @param <T>  javabean类型
     * @param from 源对象集合
     * @param to   目标类型
     * @return 目标类型对象列表
     */
    public static <T> List<T> copyMany(Collection<?> from, Class<T> to) {
        if (from == null || from.size() == 0) {
            return null;
        }

        List<T> results = new ArrayList<>();
        BeanCopier copier = getCopier(from.stream().findFirst().get().getClass(), to);
        for (Object obj : from) {
            T toObj = getInstance(to);
            copier.copy(obj, toObj, null);
            results.add(toObj);
        }

        return results;
    }

    private static <T> T getInstance(Class<T> clazz) {
        T inst;
        try {
            inst = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalStateException("InstantiationException", e);
        }

        return inst;
    }

    private static BeanCopier getCopier(Class<?> from, Class<?> to) {
        BeanCopier copier = cache.get(from, to);
        if (copier == null) {
            copier = BeanCopier.create(from, to, false);
            cache.put(from, to, copier);
        }

        return copier;
    }
}