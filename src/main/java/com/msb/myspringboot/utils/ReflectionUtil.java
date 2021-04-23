package com.msb.myspringboot.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;
import org.reflections.scanners.TypeAnnotationsScanner;

import com.msb.myspringboot.annotation.ioc.Component;
import com.msb.myspringboot.annotation.springmvc.RestController;
import com.msb.myspringboot.core.ioc.BeanFactory;

import lombok.extern.slf4j.Slf4j;

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
	 * @param args   需要调用的参数
	 * @return 执行结果
	 */
	public static Object executeMethod(Method method, Object... args) {
		Object result = null;
		try {
			String beanName = null;
			Object targetObject;
			Class<?> targetClass = method.getDeclaringClass();
			if (targetClass.isAnnotationPresent(RestController.class)) {
				beanName = targetClass.getName();
			}
			if (targetClass.isAnnotationPresent(Component.class)) {
				Component component = targetClass.getAnnotation(Component.class);
				beanName = StringUtils.isBlank(component.value()) ? targetClass.getName() : component.value();
			}
			if (StringUtils.isNotEmpty(beanName)) {
				// 判断是否已经生成该对象了 直接在ioc的容器中取出bean并调用
				targetObject = BeanFactory.BEANS.get(beanName);
			} else {
				targetObject = method.getDeclaringClass().getDeclaredConstructor().newInstance();
			}
			// 调用对象的方法
			result = method.invoke(targetObject, args);
		} catch (IllegalAccessException | InvocationTargetException | InstantiationException | IllegalArgumentException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 扫描参数路径内的注解
	 *
	 * @param packageName 需要扫描的包路径
	 * @param annotation  需要在包路径内寻找类的注解
	 * @return Set 包含路径内所有该注解的类
	 */
	public static Set<Class<?>> scanAnnotatedClass(String[] packageName, Class<? extends Annotation> annotation) {
		// 使用反射，获取注解对应的类
		Reflections reflections = new Reflections(packageName, new TypeAnnotationsScanner());
		Set<Class<?>> result = reflections.getTypesAnnotatedWith(annotation, true);
		log.info("annotationClass : {}, size: {}", result, result.size());
		return result;

	}

	/**
	 * 通过反射生成对象
	 *
	 * @param aClass 类的类型
	 * @return 类生成的对象
	 */
	public static Object newInstance(Class<?> aClass) {
		Object instance = null;
		try {
			instance = aClass.getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			log.error("实例化对象失败 class: {}", aClass);
			e.printStackTrace();
		}
		return instance;
	}

	/**
	 * 通过反射为Bean设置属性值
	 * 
	 * @param bean       目标对象
	 * @param field      目标字段
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
	 * 从指定位置下获取接口的实现类
	 * 
	 * @param packageNames   包名数列
	 * @param fieldTypeClass 接口
	 * @return 接口实现类的集合
	 */
	public static <T> Set<Class<? extends T>> getSubClass(Object[] packageNames, Class<T> interfaceClass) {
		Reflections reflections = new Reflections(packageNames);
		return reflections.getSubTypesOf(interfaceClass);
	}

	/**
	 * 获取目标类上标注Component注解的值
	 *
	 * @param aClass       要获取注解值的类
	 * @param annotation   标注的注解
	 * @param defaultValue 默认值
	 * @return 注解标注的值
	 */
	public static String getComponentValue(Class<?> aClass, Class<? extends Component> annotation,
			String defaultValue) {
		if (!aClass.isAnnotationPresent(annotation)) {
			return defaultValue;
		}
		Component declaredAnnotation = aClass.getDeclaredAnnotation(annotation);
		return StringUtils.isBlank(declaredAnnotation.value()) ? defaultValue : declaredAnnotation.value();
	}

	/**
	 * @param targetObject
	 * @param targetMethod
	 * @param args
	 * @return
	 */
	public static Object executeTargetMethod(Object targetObject, Method targetMethod, Object[] args) {
		Object result = null;
		try {
			result = targetMethod.invoke(targetObject, args);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 反射调用无返回值的方法
	 *
	 * @param targetObject 执行的目标对象
	 * @param method       执行的方法
	 * @param args         执行的参数
	 */
	public static void executeMethodNoResult(Object aspectBean, Method method, Object... args) {
		try {
			method.invoke(aspectBean, args);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}

	}
}
