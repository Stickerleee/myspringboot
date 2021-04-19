/**
 * 
 */
package com.msb.jsonboot.core.ioc;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;

import com.msb.jsonboot.annotation.Component;
import com.msb.jsonboot.annotation.RestController;
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
	 * 存放Bean的Map key=BeanName, value=标记为Bean的类
	 */
	public static final Map<String, Object> BEANS = new ConcurrentHashMap<>(128);
	
	/**
	 * 加载Bean到Map中
	 */
	public static void loadBeans() {
		Map<Class<? extends Annotation>, Set<Class<?>>> classes = ApplicationContext.CLASSES;
		//在RestController和Component类中依次取出Bean
		classes.forEach((key,value) -> {
			if (key == Component.class) {
				for (Class<?> aClass : value) {
					Component component = aClass.getAnnotation(Component.class);
					//Bean是否有命名参数
					String beanName = StringUtils.isBlank(component.value()) ? aClass.getName() : component.value();
					Object instance = ReflectionUtil.newInstance(aClass);
					BEANS.put(beanName, instance);
				}
			}
			
			if (key == RestController.class) {
				for (Class<?> aClass : value) {
					Object instance = ReflectionUtil.newInstance(aClass);
					BEANS.put(aClass.getName(), instance);
				}
			}
		});
	}
}
