package com.msb.jsonboot.utils;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 反射工具类
 * 
 * @author Stickerleee
 * @since 2021年4月16日 下午3:30:14
 */
@Slf4j
public class ReflectionUtil {

    /**
     * @param method 目标方法
     * @param args   调用的参数
     * @return 执行结果
     */
    public static Object executeMethod(Method method, Object... args) {
        Object result = null;
        try {
            // 生成方法对应类的对象
            Object targetObject = method.getDeclaringClass().newInstance();
            // 调用对象的方法
            result = method.invoke(targetObject, args);
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
        return result;
    }
}
