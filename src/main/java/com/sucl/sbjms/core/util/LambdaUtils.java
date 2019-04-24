package com.sucl.sbjms.core.util;

import com.sucl.sbjms.core.service.Property;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author sucl
 * @date 2019/4/22
 */
public final class LambdaUtils {

    private static final Map<String, Map<String, String>> LAMBDA_CACHE = new ConcurrentHashMap<>();

    private static final Map<Class, WeakReference<SerializedLambda>> FUNC_CACHE = new ConcurrentHashMap<>();

    /**
     * <p>
     * 解析 lambda 表达式
     * </p>
     *
     * @param func 需要解析的 lambda 对象
     * @param <T>  类型，被调用的 Function 对象的目标类型
     * @return 返回解析后的结果
     */
    public static <T> SerializedLambda resolve(Property<T, ?> func) {
        Class clazz = func.getClass();
        return Optional.ofNullable(FUNC_CACHE.get(clazz))
                .map(WeakReference::get)
                .orElseGet(() -> {
                    SerializedLambda lambda = SerializedLambda.convert(func);
                    FUNC_CACHE.put(clazz, new WeakReference<>(lambda));
                    return lambda;
                });
    }

    /**
     * 保存缓存信息
     *
     * @param className 类名
     * @param property  属性
     * @param sqlSelect 字段搜索
     */
    private static void saveCache(String className, String property, String sqlSelect) {
        Map<String, String> cacheMap = LAMBDA_CACHE.getOrDefault(className, new HashMap<>());
        cacheMap.put(property, sqlSelect);
        LAMBDA_CACHE.put(className, cacheMap);
    }


    /**
     * <p>
     * 获取实体对应字段 MAP
     * </p>
     *
     * @param entityClassName 实体类名
     * @return 缓存 map
     */
    public static Map<String, String> getColumnMap(String entityClassName) {
        return LAMBDA_CACHE.get(entityClassName);
    }
}

