/**
 * 
 */
package com.msb.jsonboot.core.ioc;

import java.lang.reflect.Field;
import java.util.Map;

import com.msb.jsonboot.annotation.Autowired;
import com.msb.jsonboot.utils.ReflectionUtil;

/**
 * 依赖注入
 * 
 * @author Stickerleee
 * @since 2021年4月19日 上午9:31:41
 */
public class DependencyInjection {

	/**
	 * @param packageName
	 */
	public static void loadDependency(String packageName) {
		//获取储存BEAN的Map
		Map<String, Object> beanMap = BeanFactory.BEANS;
		//遍历每个Bean
		beanMap.forEach((beanName, bean) -> {
			//获取每个Bean作用域中的所有字段
			for (Field field : bean.getClass().getDeclaredFields()) {
				//如果该字段有Autowired注解
				if (field.isAnnotationPresent(Autowired.class)) {
					String className = field.getName();
					Object targetBean = beanMap.get(className);
					if (targetBean != null) {
						ReflectionUtil.setReflectionField(bean, field, targetBean);
					}
				}
			}
		});
	}

	
}
