package com.example.backend_scaffold.infrastructure.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * JSON工具类
 * <p>
 * 提供JSON序列化和反序列化的方法
 * </p>
 *
 * @author example
 */
public class JsonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        // 配置ObjectMapper
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        // 注册Java 8日期时间模块
        objectMapper.registerModule(new JavaTimeModule());
    }

    /**
     * 将对象转换为JSON字符串
     *
     * @param object 要转换的对象
     * @return JSON字符串
     */
    public static String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("转换对象到JSON字符串失败", e);
        }
    }

    /**
     * 将对象转换为格式化的JSON字符串
     *
     * @param object 要转换的对象
     * @return 格式化的JSON字符串
     */
    public static String toPrettyJson(Object object) {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("转换对象到格式化JSON字符串失败", e);
        }
    }

    /**
     * 将JSON字符串转换为指定类型的对象
     *
     * @param json  JSON字符串
     * @param clazz 目标类型
     * @param <T>   泛型参数
     * @return 转换后的对象
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new RuntimeException("从JSON字符串转换对象失败", e);
        }
    }

    /**
     * 将JSON字符串转换为指定类型引用的对象
     * <p>
     * 用于处理泛型类型，如List<Bean>, Map<String, Bean>等
     * </p>
     *
     * @param json          JSON字符串
     * @param typeReference 类型引用
     * @param <T>           泛型参数
     * @return 转换后的对象
     */
    public static <T> T fromJson(String json, TypeReference<T> typeReference) {
        try {
            return objectMapper.readValue(json, typeReference);
        } catch (IOException e) {
            throw new RuntimeException("从JSON字符串转换复杂类型对象失败", e);
        }
    }

    /**
     * 将JSON字符串转换为List集合
     *
     * @param json  JSON字符串
     * @param clazz 集合元素类型
     * @param <T>   泛型参数
     * @return List集合
     */
    public static <T> List<T> fromJsonToList(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (IOException e) {
            throw new RuntimeException("从JSON字符串转换List失败", e);
        }
    }

    /**
     * 将JSON字符串转换为Map
     *
     * @param json       JSON字符串
     * @param keyClass   键类型
     * @param valueClass 值类型
     * @param <K>        键泛型参数
     * @param <V>        值泛型参数
     * @return Map对象
     */
    public static <K, V> Map<K, V> fromJsonToMap(String json, Class<K> keyClass, Class<V> valueClass) {
        try {
            return objectMapper.readValue(json, objectMapper.getTypeFactory().constructMapType(Map.class, keyClass, valueClass));
        } catch (IOException e) {
            throw new RuntimeException("从JSON字符串转换Map失败", e);
        }
    }

    /**
     * 获取ObjectMapper实例
     * <p>
     * 用于需要更高级自定义配置的场景
     * </p>
     *
     * @return ObjectMapper实例
     */
    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}