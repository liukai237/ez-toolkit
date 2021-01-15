package com.iakuil.toolkit;


import java.lang.reflect.Array;
import java.util.*;

/**
 * Map链式Builder
 *
 * <p>Value为Null、空字符串、空数组、空集合、空Map等时会被过滤掉</p>
 */
public class MapBuilder {
    private Map<String, Object> tmp;

    private MapBuilder() {
        this.tmp = new LinkedHashMap<>();
    }

    private MapBuilder(Map<String, Object> map) {
        this();
        this.tmp.putAll(map);
    }

    public static MapBuilder init() {
        return new MapBuilder();
    }

    public static MapBuilder init(Map<String, Object> map) {
        return map == null ? new MapBuilder() : new MapBuilder(map);
    }

    public Map<String, Object> build() {
        removeEmptyEntry();
        return this.tmp;
    }

    public MapBuilder append(String key, Object value) {
        this.tmp.put(key, value);
        return this;
    }

    public MapBuilder remove(String key) {
        this.tmp.remove(key);
        return this;
    }

    public MapBuilder rename(String oldName, String newName) {
        Object v = this.tmp.get(oldName);
        this.tmp.remove(oldName);
        this.tmp.put(newName, v);
        return this;
    }

    private void removeEmptyEntry() {
        this.tmp.entrySet().removeIf(entry -> isEmptyOrNull(entry.getValue()));
    }

    private boolean isEmptyOrNull(Object obj) {
        boolean result;

        if (obj == null) {
            result = true;
        } else if (obj instanceof String) {
            result = ((String) obj).isEmpty();
        } else if (obj instanceof Collection<?>) {
            return ((Collection<?>) obj).isEmpty();
        } else if (obj instanceof Iterable<?>) {
            Iterable<?> iterable = (Iterable<?>) obj;
            Iterator<?> iterator = iterable.iterator();
            return !iterator.hasNext();
        } else if (obj instanceof Map<?, ?>) {
            return ((Map<?, ?>) obj).isEmpty();
        } else if (obj instanceof Object[]) {
            return ((Object[]) obj).length == 0;
        } else if (obj instanceof Iterator<?>) {
            return !((Iterator<?>) obj).hasNext();
        } else if (obj instanceof Enumeration<?>) {
            return !((Enumeration<?>) obj).hasMoreElements();
        } else {
            try {
                return Array.getLength(obj) == 0;
            } catch (final IllegalArgumentException ex) {
                throw new IllegalArgumentException("Unsupported object type: " + obj.getClass().getName());
            }
        }

        return result;
    }
}