package com.iakuil.toolkit;

import com.jayway.jsonpath.*;
import com.jayway.jsonpath.spi.json.JacksonJsonProvider;
import com.jayway.jsonpath.spi.json.JsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import com.jayway.jsonpath.spi.mapper.MappingProvider;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 使用Json Path的方式访问JSON
 *
 * <p>读出的值尽量不要有嵌套，否则可能出现泛型丢失。
 *
 * @author Kai
 */
public class JsonPathUtils {

    private static final TypeRef<Map<String, Object>> MAP_TYPE_REF = new TypeRef<Map<String, Object>>() {
    };

    private static final Configuration CONFIG;

    static {
        Configuration.setDefaults(new Configuration.Defaults() {
            private final JsonProvider jsonProvider = new JacksonJsonProvider();
            private final MappingProvider mappingProvider = new JacksonMappingProvider();

            @Override
            public JsonProvider jsonProvider() {
                return jsonProvider;
            }

            @Override
            public MappingProvider mappingProvider() {
                return mappingProvider;
            }

            @Override
            public Set<Option> options() {
                return EnumSet.noneOf(Option.class);
            }
        });

        CONFIG = Configuration.defaultConfiguration().addOptions(Option.DEFAULT_PATH_LEAF_TO_NULL, Option.SUPPRESS_EXCEPTIONS);
    }

    private JsonPathUtils() {
    }

    /**
     * 为JSON节点赋值
     *
     * @param json  json数据
     * @param path  json path
     * @param key   json key
     * @param value json value
     * @return String类型
     */
    public static String put(String json, String path, String key, Object value) {
        return parse(json).put(path, key, value).jsonString();
    }

    /**
     * 为JSON数组追加元素
     *
     * @param json json数据
     * @param path json path
     * @param obj  json value
     * @return String类型
     */
    public static String add(String json, String path, Object obj) {
        return parse(json).add(path, obj).jsonString();
    }

    /**
     * 删除JSON节点
     *
     * @param json json数据
     * @param path json path
     * @return String类型
     */
    public static String del(String json, String path) {
        return parse(json).delete(path).jsonString();
    }

    /**
     * 读取为String
     *
     * @param json json数据
     * @param path json path
     * @return String类型
     */
    public static String readStr(String json, String path) {
        return read(json, path, String.class);
    }

    /**
     * 读取为Long
     *
     * @param json json数据
     * @param path json path
     * @return Long类型
     */
    public static Long readLong(String json, String path) {
        return read(json, path, Long.class);
    }

    /**
     * 读取为Integer
     *
     * @param json json数据
     * @param path json path
     * @return Integer类型
     */
    public static Integer readInt(String json, String path) {
        return read(json, path, Integer.class);
    }

    /**
     * 读取为Double
     *
     * @param json json数据
     * @param path json path
     * @return Double类型
     */
    public static Double readDouble(String json, String path) {
        return read(json, path, Double.class);
    }

    /**
     * 读取为Map
     *
     * @param json json数据
     * @param path json path
     * @return Map类型数据
     */
    public static Map<String, Object> readMap(String json, String path) {
        return read(json, path, MAP_TYPE_REF);
    }

    /**
     * 读取为List
     * <p>可以带泛型，但是多层嵌套后泛型无效，会被读取为LinkedHashMap。
     *
     * @param <T>   javabean类型
     * @param json  json数据
     * @param path  json path
     * @param clazz 期望类型
     * @return 指定类型的javabean列表
     */
    public static <T> List<T> readList(String json, String path, Class<T> clazz) {
        TypeRef<List<T>> typeRef = new TypeRef<List<T>>() {
        };
        return read(json, path, typeRef);
    }

    /**
     * 读取为Java对象
     * <p>可以带泛型，但是多层嵌套后泛型无效，会被读取为LinkedHashMap。
     *
     * @param <T>   javabean类型
     * @param json  json数据
     * @param path  json path
     * @param clazz 期望类型
     * @return 指定类型的javabean
     */
    public static <T> T read(String json, String path, Class<T> clazz) {
        return parse(json).read(path, clazz);
    }

    private static <T> T read(String json, String path, TypeRef<T> tr) {
        return parse(json).read(path, tr);
    }

    private static DocumentContext parse(String json) {
        return JsonPath
                .using(CONFIG)
                .parse(json);
    }
}
