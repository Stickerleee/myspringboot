/**
 * 
 */
package com.msb.jsonboot.core.ioc;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;

import com.msb.jsonboot.annotation.aop.Aspect;
import com.msb.jsonboot.annotation.ioc.Component;
import com.msb.jsonboot.core.context.ApplicationContext;
import com.msb.jsonboot.utils.ReflectionUtil;

/**
 * 整合Bean
 * 
 * @author Stickerleee
 * @since 2021年4月17日 下午1:37:12
 */
public class BeanFactory {

	/**
	 * 存放Bean的Map key=BeanName, value=标记为Bean的类 一级缓存
	 */
	public static final Map<String, Object> BEANS = new ConcurrentHashMap<>(128);
	
	/**
	 * 存放的aop对象 二级缓存
	 */
	public static final Map<String, Object> EARLY_BEANS = new HashMap<>(16);
	
	/**
	 * 存放已实例化但未初始化的基础对象 三级缓存
	 */
	public static final Map<String, Object> BASIC_OBJECTS = new HashMap<>(16);
	
	/**
	 * 记录当前正在实例化的对象名
	 */
	public static final List<String> CURRENT_IN_CREATION = new LinkedList<>();
   
	/**
     * 存放含 @Aspect 注解记录的类型
     */
    private static final Map<String, String[]> BEAN_TYPE_NAME_MAP = new ConcurrentHashMap<>(128);
	
    /**
	 * 加载Bean 初始化Bean
	 */
	public static void loadBeans() {
		Map<Class<? extends Annotation>, Set<Class<?>>> classes = ApplicationContext.CLASSES;
		//初始化Aspect
		Set<Class<?>> aspectSet = classes.get(Aspect.class);
        BEAN_TYPE_NAME_MAP.put(Aspect.class.getName(),
                aspectSet.stream().map(Class::getName).toArray(size -> new String[size]));
        for (Class<?> aspectClass : aspectSet){
            getBean(aspectClass.getName(), aspectClass);
        }
		//依次取出Bean
		classes.forEach((key,value) -> {
			for (Class<?> aClass : value) {
				String beanName = aClass.getName();
				if (key == Component.class) {
					Component component = aClass.getAnnotation(Component.class);
					//判断Component是否有命名参数
					beanName = StringUtils.isBlank(component.value()) ? beanName : component.value();
				}
				//通过getBean创建bean
				getBean(beanName, aClass);
			}
		});
	}
	/**
	 * 从一二三级缓存中查找并获取类实例 或者创建新的类实例
	 * 
	 * @param beanName bean名字
	 * @param aClass 实例化的类
	 */
	static Object getBean(String beanName, Class<?> aClass) {
		//从一级缓存中获取bean
		Object bean = BEANS.get(beanName);
		if (bean != null) {
			return bean;
		}
		if ((bean = EARLY_BEANS.get(beanName)) == null && (bean = BASIC_OBJECTS.get(beanName)) == null) {
			//若bean也不在二三级缓存中 则重新创建bean实例
			bean = ReflectionUtil.newInstance(aClass);
			//放入三级缓存中
			BASIC_OBJECTS.put(beanName, bean);
			//记录正在创建的bean
			CURRENT_IN_CREATION.add(beanName);
		} else if (CURRENT_IN_CREATION.contains(beanName)) {
			//若bean正在创建
			return bean;
		}
		//注入依赖
		DependencyInjection.loadDependency(bean);
		//创建bean完成 清除二三级缓存 存入一级缓存
        BEANS.put(beanName, bean);
        EARLY_BEANS.remove(beanName);
        BASIC_OBJECTS.remove(beanName);
        CURRENT_IN_CREATION.remove(beanName);
        return bean;
	}
}
