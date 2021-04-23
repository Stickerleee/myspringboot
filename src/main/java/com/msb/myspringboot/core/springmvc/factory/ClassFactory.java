/**
 * 
 */
package com.msb.myspringboot.core.springmvc.factory;

import java.util.Set;

import com.msb.myspringboot.annotation.aop.Aspect;
import com.msb.myspringboot.annotation.ioc.Component;
import com.msb.myspringboot.annotation.springmvc.RestController;
import com.msb.myspringboot.core.context.ApplicationContext;
import com.msb.myspringboot.utils.ReflectionUtil;

/**
 * 启动时初始化类
 * 
 * @author Stickerleee
 * @since 2021年4月17日 上午11:03:10
 */
public class ClassFactory {

	/**
	 * 提取目标包路径下的含RestController，Component，Aspect注解的类
	 * 
	 * @param packageName
	 */
	public static void loadClass() {
		String[] packageName = ApplicationContext.getInstance().getPackageNames();
		// 整合@RestController注解
		Set<Class<?>> restControllerClasses = ReflectionUtil.scanAnnotatedClass(packageName, RestController.class);
		ApplicationContext.CLASSES.put(RestController.class, restControllerClasses);
		// 整合@Component注解
		Set<Class<?>> componentClasses = ReflectionUtil.scanAnnotatedClass(packageName, Component.class);
		ApplicationContext.CLASSES.put(Component.class, componentClasses);
		// 整合@Aspect注解
		Set<Class<?>> aspectClasses = ReflectionUtil.scanAnnotatedClass(packageName, Aspect.class);
		ApplicationContext.CLASSES.put(Aspect.class, aspectClasses);
	}
}
