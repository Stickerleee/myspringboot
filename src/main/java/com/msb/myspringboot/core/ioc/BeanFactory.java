/**
 * 
 */
package com.msb.myspringboot.core.ioc;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;

import com.msb.myspringboot.annotation.aop.Aspect;
import com.msb.myspringboot.annotation.ioc.Component;
import com.msb.myspringboot.core.aop.factory.BeanPostProcessorFactory;
import com.msb.myspringboot.core.aop.process.BeanPostProcessor;
import com.msb.myspringboot.core.context.ApplicationContext;
import com.msb.myspringboot.utils.ReflectionUtil;

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
	 * 存放含某些注解的对象名称，目前只有@Aspect
	 */
	private static final Map<String, String[]> BEAN_TYPE_NAME_MAP = new ConcurrentHashMap<>(128);

	/**
	 * 加载Bean 初始化Bean
	 */
	public static void loadBeans() {
		Map<Class<? extends Annotation>, Set<Class<?>>> classes = ApplicationContext.CLASSES;
		// 初始化Aspect
		Set<Class<?>> aspectSet = classes.get(Aspect.class);
		BEAN_TYPE_NAME_MAP.put(Aspect.class.getName(),
				aspectSet.stream().map(Class::getName).toArray(size -> new String[size]));
		for (Class<?> aspectClass : aspectSet) {
			getBean(aspectClass.getName(), aspectClass);
		}

		// 依次取出Bean
		classes.forEach((key, value) -> {
			for (Class<?> aClass : value) {
				String beanName = aClass.getName();
				if (key == Component.class) {
					Component component = aClass.getAnnotation(Component.class);
					// 判断Component是否有命名参数
					beanName = StringUtils.isBlank(component.value()) ? beanName : component.value();
				}
				// 通过getBean创建bean
				getBean(beanName, aClass);
			}
		});
	}

	/**
	 * 从一二三级缓存中查找并获取类实例 或者创建新的类实例
	 * 
	 * @param beanName bean名字
	 * @param aClass   实例化的类
	 */
	static Object getBean(String beanName, Class<?> aClass) {
		// 从一级缓存中获取bean
		Object bean = BEANS.get(beanName);
		if (bean != null) {
			return bean;
		}
		if ((bean = EARLY_BEANS.get(beanName)) == null && (bean = BASIC_OBJECTS.get(beanName)) == null) {
			// 若bean均不在二三级缓存中 则重新创建bean实例
			bean = ReflectionUtil.newInstance(aClass);
			// 放入三级缓存中
			BASIC_OBJECTS.put(beanName, bean);
			// 记录正在创建的bean
			CURRENT_IN_CREATION.add(beanName);
		} else if (CURRENT_IN_CREATION.contains(beanName)) {
			// 若bean正在创建 应该从二级或三级缓存中寻找
			return getProxyBean(beanName, bean);
		}
		// 注入依赖
		DependencyInjection.loadDependency(bean);
		bean = getProxyBean(beanName, bean);
		// 创建bean完成 清除二三级缓存 存入一级缓存
		BEANS.put(beanName, bean);
		EARLY_BEANS.remove(beanName);
		BASIC_OBJECTS.remove(beanName);
		CURRENT_IN_CREATION.remove(beanName);
		return bean;
	}

	/**
	 * 根据注解名称获取Bean对象
	 * 
	 * @param name bean的名称
	 * @return 实例对象
	 */
	public static Set<Object> getBeansByName(String name) {
		Set<Object> result = new HashSet<>();
		String[] beanNames = BEAN_TYPE_NAME_MAP.get(name);
		if (Objects.isNull(beanNames)) {
			return result;
		}
		for (String beanName : beanNames) {
			Object bean = BEANS.get(beanName);
			if (!Objects.isNull(bean)) {
				result.add(bean);
			}
		}
		return result;
	}

	/**
	 * 从二级缓存中获取代理bean 或创建一个代理bean
	 * 
	 * @param beanName
	 * @param bean
	 * @return 代理包装的Bean
	 */
	private static Object getProxyBean(String beanName, Object bean) {
		// 二级缓存中有此bean，已经创建过代理，直接返回bean
		if (!Objects.isNull(EARLY_BEANS.get(beanName))) {
			return bean;
		}
		// 创建代理Bean
		BeanPostProcessor beanPostProcessor = BeanPostProcessorFactory.getBeanPostProcessor(bean);
		bean = beanPostProcessor.postProcessAfterInitialization(bean);
		// 将生成的bean存到二级缓存中，如果需要代理则存入的是代理bean， 如果不需要，则是正常bean
		EARLY_BEANS.put(beanName, bean);
		return bean;
	}
}
