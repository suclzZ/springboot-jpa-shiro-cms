package com.sucl.sbjms.core.util;

import com.sucl.sbjms.core.service.Property;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//import java.lang.invoke.SerializedLambda;

/**
 * 通过lamdba表示对象属性名
 * 比如在jpa中的操作
 * @author sucl
 * @date 2019/4/22
 */
public final class LambdaUtils {
    public static final String IS = "is";

    private static final Map<String, Map<String, String>> LAMBDA_CACHE = new ConcurrentHashMap<>();

    /**
     * 参考mybatis-plus ，但为什么需要重新建一个同名同序列号的SerializedLambda类，同时里面的方法都要删除
     * @param func
     * @param <T>
     * @return
     */
    public static <T> SerializedLambda resolve(Property<T, ?> func) {
        Class clazz = func.getClass();
        SerializedLambda lambda = SerializedLambda.convert(func);
        return lambda;
    }

    /**
     * lamdba 内置方法writeReplace返回值为java.lang.invoke.SerializedLambda
     * @param fn
     * @param <T>
     * @return
     */
    public static <T> String getPropertyName(Property<T,?> fn) {
        try {
            Method method = fn.getClass().getDeclaredMethod("writeReplace");
            method.setAccessible(Boolean.TRUE);
            java.lang.invoke.SerializedLambda serializedLambda = (java.lang.invoke.SerializedLambda) method.invoke(fn);
            String getter = serializedLambda.getImplMethodName();
//            String fieldName = Introspector.decapitalize(getter.replace("get", ""));
            return resolveFieldName(getter);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }


    public static String resolveFieldName(String getMethodName) {
        if (getMethodName.startsWith("get")) {
            getMethodName = getMethodName.substring(3);
        } else if (getMethodName.startsWith(IS)) {
            getMethodName = getMethodName.substring(2);
        }
        return StringUtils.uncapitalize(getMethodName);
    }

}

