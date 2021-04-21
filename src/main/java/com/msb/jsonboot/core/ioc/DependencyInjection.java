package com.msb.jsonboot.core.ioc;

import java.lang.reflect.Field;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import com.msb.jsonboot.annotation.ioc.*;
import com.msb.jsonboot.exception.InterfaceNotExistsImplementException;
import com.msb.jsonboot.exception.NotFountTargetBeanException;
import com.msb.jsonboot.core.context.ApplicationContext;
import com.msb.jsonboot.utils.ReflectionUtil;

/**
 * 依赖注入
 * 
 * @author Stickerleee
 * @since 2021年4月19日 上午9:31:41
 */
public class DependencyInjection {

	/**
	 * @param bean 对指定的bean进行注入
	 */
	public static void loadDependency(Object bean) {
        if (bean == null){
            return;
        }
		//获取Bean作用域中的所有字段
		for (Field field : bean.getClass().getDeclaredFields()) {
			//如果该字段有Autowired注解
			if (field.isAnnotationPresent(Autowired.class)){
				//目标类型
                Class<?> fieldTypeClass = field.getType();
                //目标类型名称
                String beanName = fieldTypeClass.getName();
                //判断是否是接口 如果是接口那存着的就是接口信息，需要顺延找到实现类
                if (fieldTypeClass.isInterface()){
                    Set<Class<?>> subClass = ReflectionUtil.getSubClass(ApplicationContext.getInstance().packageName, fieldTypeClass);
                    int sizeOfSet = subClass.size();
                    if (sizeOfSet == 0) {
                    	//无实现类时抛出错误
                    	throw new InterfaceNotExistsImplementException("该接口的实现类不存在" + beanName);
                    } else if (sizeOfSet == 1){
                    	//单个实现类
                    	Class<?> aClass = subClass.iterator().next();
                    	fieldTypeClass = aClass;
                        beanName = ReflectionUtil.getComponentValue(aClass, Component.class, aClass.getName());
                    } else {
                    	//存在多个实现类时，使用@Qulifer指定需要注入的实现类名称，即@Component的参数
                    	Qualifier qualifier = field.getDeclaredAnnotation(Qualifier.class);
                        if (qualifier == null || StringUtils.isBlank(qualifier.value())){
                            throw new NotFountTargetBeanException("该依赖没有找到目标类：" + field.toString());
                        }
                        //循环subClass 寻找与@Qualifier参数同名的Class
                        for (Class<?> aClass : subClass){
                            beanName  = ReflectionUtil.getComponentValue(aClass, Component.class, aClass.getName());
                            if (beanName.equals(qualifier.value())){
                            	fieldTypeClass = aClass;
                                beanName = qualifier.value();
                                break;
                            }
                        }
                    }
                }
                //根据类名获取类实例
                Object targetBean = BeanFactory.getBean(beanName, fieldTypeClass);
                if (targetBean != null){
                    ReflectionUtil.setReflectionField(bean, field, targetBean);
                } else {
                	throw new NotFountTargetBeanException("该依赖没有找到目标类：" + field.toString());
                }
            }
		}
	}
}
