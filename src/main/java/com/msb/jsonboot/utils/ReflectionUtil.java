package com.msb.jsonboot.utils;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;

import com.msb.jsonboot.annotation.*;
import com.msb.jsonboot.core.ioc.BeanFactory;

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
     * @param args 需要调用的参数
     * @return 执行结果
     */
    public static Object executeMethod(Method method, Object... args) {
    	Object result = null;
        try {
            String beanName = null;
            Object targetObject;
            Class<?> targetClass = method.getDeclaringClass();
            if (targetClass.isAnnotationPresent(RestController.class)){
                beanName = targetClass.getName();
            }
            if (targetClass.isAnnotationPresent(Component.class)){
                Component component = targetClass.getAnnotation(Component.class);
                beanName = StringUtils.isBlank(component.value()) ? targetClass.getName() : component.value();
            }
            if (StringUtils.isNotEmpty(beanName)){
            	//判断是否已经生成该对象了 直接在ioc的容器中取出bean并调用
                targetObject = BeanFactory.BEANS.get(beanName);
            }else{
                targetObject = method.getDeclaringClass().getDeclaredConstructor().newInstance();
            }
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
	 * @param bean 目标对象
	 * @param field 目标字段
	 * @param targetBean 属性值
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
	/**
	 * 从指定包名下获取接口的实现类
	 * 
	 * @param packageName 包名
	 * @param fieldTypeClass 接口
	 * @return 接口实现类集合
	 */
	public static Set<Class<?>> getSubClass(String packageName, Class<?> interfaceClass) {
		Reflections reflections = new Reflections(packageName);
		return reflections.getSubTypesOf((Class<Object>) interfaceClass);
	}
    /**
     * 获取目标类上标注Component注解的值
     *
     * @param aClass 要获取注解值的类
     * @param annotation 标注的注解
     * @param defaultValue 默认值
     * @return 注解标注的值
     */
    public static String getComponentValue(Class<?> aClass, Class<? extends Component> annotation
                                                , String defaultValue){
        if (!aClass.isAnnotationPresent(annotation)){
            return defaultValue;
        }
        Component declaredAnnotation = aClass.getDeclaredAnnotation(annotation);
        return StringUtils.isBlank(declaredAnnotation.value()) ? defaultValue : declaredAnnotation.value();
    }
}
