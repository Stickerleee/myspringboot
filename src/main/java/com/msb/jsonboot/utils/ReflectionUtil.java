package com.msb.jsonboot.utils;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
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
     * 通过反射调用方法
     * 
     * @param method 目标方法
     * @param args   调用的参数
     * @return 执行结果
     */
    public static Object executeMethod(Method method, Object... args) {
        Object result = null;
        try {
            // 生成方法对应类的对象
            Object targetObject = method.getDeclaringClass().getDeclaredConstructor().newInstance();
            // 调用对象的方法
            result = method.invoke(targetObject, args);
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException | IllegalArgumentException | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    /**
     * 通过反射生成对象
     *
     * @param aClass 类的类型
     * @return 类生成的对象
     */
    public static Object newInstance(Class<?> aClass){
        Object instance = null;
        try {
            instance = aClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            log.error("实例化对象失败 class: {}", aClass);
            e.printStackTrace();
        }
        return instance;
    }

	/**
	 * 通过反射为Bean设置属性值
	 * @param bean
	 * @param field
	 * @param targetBean
	 */
	public static void setReflectionField(Object bean, Field field, Object targetBean) {
		try {
			field.setAccessible(true);
			field.set(bean, targetBean);
		} catch (IllegalAccessException e) {
            log.error("设置对象field失败");
            e.printStackTrace();
		}
		
	}
}
