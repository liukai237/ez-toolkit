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
    private static final MultiKeyMap<Class<?>, BeanCopier> BEAN_TYPE_CACHE = new MultiKeyMap<>();

    private BeanUtils() {
    }

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
            inst = clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new IllegalStateException("Occurring an exception during object instancing!", e);
        }

        return inst;
    }

    private static BeanCopier getCopier(Class<?> from, Class<?> to) {
        BeanCopier copier = BEAN_TYPE_CACHE.get(from, to);
        if (copier == null) {
            copier = BeanCopier.create(from, to, false);
            BEAN_TYPE_CACHE.put(from, to, copier);
        }

        return copier;
    }
}
