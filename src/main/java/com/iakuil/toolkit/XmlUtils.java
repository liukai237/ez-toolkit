package com.iakuil.toolkit;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.IOException;
import java.io.InputStream;

/**
 * 基于Jackson的XML工具类
 *
 * @author Kai
 */
public class XmlUtils {

    private static final XmlMapper XML_MAPPER;

    static {
        XML_MAPPER = new XmlMapper();
        XML_MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        XML_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    /**
     * JSON转换为javabean
     *
     * @param <T>    javabean类型
     * @param xmlStr XML字符串
     * @param clazz  期望类型
     * @return 指定类型的javabean
     */
    public static <T> T xml2bean(String xmlStr, Class<T> clazz) {
        try {
            return XML_MAPPER.readValue(xmlStr, clazz);
        } catch (IOException e) {
            throw new IllegalStateException("Occurring an exception during xml parsing!" + e);
        }
    }

    /**
     * JSON转换为javabean
     *
     * @param <T>   javabean类型
     * @param xmlIs XML流
     * @param clazz 期望类型
     * @return 指定类型的javabean
     */
    public static <T> T xml2bean(InputStream xmlIs, Class<T> clazz) {
        try {
            return XML_MAPPER.readValue(xmlIs, clazz);
        } catch (IOException e) {
            throw new IllegalStateException("Occurring an exception during xml parsing!" + e);
        }
    }

    /**
     * javabean转换为XML
     *
     * @param obj javabean对象
     * @return JSON数据
     */
    public static String bean2Xml(Object obj) {
        try {
            return XML_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Occurring an exception during object parsing!" + e);
        }
    }
}
