package com.msb.jsonboot.core.ioc;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;

import com.msb.jsonboot.annotation.Qualifier;
import com.msb.jsonboot.exception.InterfaceNotExistsImplementException;
import com.msb.jsonboot.exception.NotFountTargetBeanException;
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
				if (field.isAnnotationPresent(Autowired.class)){
                    Class<?> fieldTypeClass = field.getType();
                    String className= fieldTypeClass.getName();
                    //判断是否是接口 如果是接口那存着的就是接口信息，需要顺延找到实现类
                    if (fieldTypeClass.isInterface()){
                        Set<Class<?>> subClass = getSubClass(packageName, fieldTypeClass);
                        int setSize = subClass.size();
                        if (setSize == 0) {
                        	//无实现类时抛出错误
                        	throw new InterfaceNotExistsImplementException("该接口的实现类不存在" + className);
                        } else if (setSize == 1){
                        	//单个实现类
                            className = subClass.iterator().next().getName();
                        } else {
                        	//存在多个实现类，使用@Qulifer指定需要注入的实现类名称
                        	Qualifier qualifier = field.getDeclaredAnnotation(Qualifier.class);
                        	className = StringUtils.isBlank(qualifier.value()) ? beanName : qualifier.value();
                        }
                        
                    }
                    //根据类名获取类
                    Object targetBean = beanMap.get(className);
                    if (targetBean != null){
                        ReflectionUtil.setReflectionField(bean, field, targetBean);
                    } else {
                    	throw new NotFountTargetBeanException("该依赖没有找到目标类：" + field.toString());
                    }
                }
			}
		});
	}

	/**
	 * 获取接口的实现类
	 * 
	 * @param packageName 包名
	 * @param fieldTypeClass 接口
	 * @return 接口实现类集合
	 */
	private static Set<Class<?>> getSubClass(String packageName, Class<?> interfaceClass) {
		Reflections reflections = new Reflections(packageName);
		return reflections.getSubTypesOf((Class<Object>) interfaceClass);
	}

	
}
