package com.iakuil.toolkit;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeBase;

import java.io.IOException;
import java.util.*;

/**
 * 基于Jackson的JSON工具类
 *
 * @author Kai
 */
public class JsonUtils {
    private static final ObjectMapper OBJECT_MAPPER;

    static {
        OBJECT_MAPPER = new ObjectMapper();
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private JsonUtils() {
    }

    /**
     * javabean转换为JSON
     *
     * @param obj javabean对象
     * @return JSON数据
     */
    public static String bean2Json(Object obj) {
        if (obj == null) {
            return null;
        }

        String result;
        try {
            result = OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Occurring an exception during object parsing!", e);
        }

        return result;
    }

    /**
     * JSON转换为javabean
     *
     * @param <T>     javabean类型
     * @param jsonStr json数据
     * @param clazz   期望类型
     * @return 指定类型的javabean
     */
    public static <T> T json2bean(String jsonStr, Class<T> clazz) {
        if (jsonStr == null || "".equals(jsonStr) || clazz == null) {
            return null;
        }

        T result;

        try {
            result = OBJECT_MAPPER.readValue(jsonStr, clazz);
        } catch (IOException e) {
            throw new IllegalStateException("Occurring an exception during json parsing!", e);
        }

        return result;
    }

    /**
     * JSON转换为Map
     *
     * @param jsonStr json数据
     * @return Map<String, Object>类型
     */
    public static Map<String, Object> json2Map(String jsonStr) {
        if (jsonStr == null || "".equals(jsonStr)) {
            return null;
        }

        Map<String, Object> result;
        try {
            result = OBJECT_MAPPER.readValue(jsonStr, new TypeReference<Map<String, Object>>() {
            });
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Occurring an exception during json parsing!", e);
        }
        return result;
    }

    /**
     * JSON转换为javabean列表
     *
     * @param <T>     javabean类型
     * @param jsonStr json数据
     * @param clazz   期望类型
     * @return 指定类型的javabean列表
     */
    public static <T> List<T> json2List(String jsonStr, Class<T> clazz) {
        return readType(jsonStr, OBJECT_MAPPER.getTypeFactory().constructCollectionType(ArrayList.class, clazz));
    }

    /**
     * JSON转换为javabean Set
     *
     * @param <T>     javabean类型
     * @param jsonStr json数据
     * @param clazz   期望类型
     * @return 指定类型的javabean Set
     */
    public static <T> Set<T> json2Set(String jsonStr, Class<T> clazz) {
        return readType(jsonStr, OBJECT_MAPPER.getTypeFactory().constructCollectionType(HashSet.class, clazz));
    }

    /**
     * JSON转换为javabean数组
     *
     * @param <T>     javabean类型
     * @param jsonStr json数据
     * @param clazz   期望类型
     * @return 指定类型的javabean数组
     */
    public static <T> T[] json2Array(String jsonStr, Class<T> clazz) {
        return readType(jsonStr, OBJECT_MAPPER.getTypeFactory().constructArrayType(clazz));
    }

    private static <T> T readType(String jsonStr, TypeBase type) {
        if (jsonStr == null || "".equals(jsonStr) || type == null) {
            return null;
        }

        T result;
        try {
            result = OBJECT_MAPPER.readValue(jsonStr, type);
        } catch (IOException e) {
            throw new IllegalStateException("Occurring an exception during json parsing!", e);
        }

        return result;
    }
}
