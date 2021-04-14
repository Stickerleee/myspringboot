package com.msb.jsonboot.core.scanners;

import java.lang.annotation.Annotation;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.TypeAnnotationsScanner;

/**
 * 注解解析类，为注解实现作用
 *
 */
public class AnnotatedClassScanner {
	
    /**
     * 实现路径内注解扫描功能
     *
     * @param packageName 需要扫描的包路径
     * @param annotation 需要在包路径内寻找类的注解
     * @return 包路径内包含该注解的类
     */
	public Set<Class<?>> scan(String packageName, Class<? extends Annotation> annotation){
		//使用反射，获取注解对应的类
		Reflections reflections = new Reflections(packageName, new TypeAnnotationsScanner());
		Set<Class<?>> result = reflections.getTypesAnnotatedWith(annotation, true);
		
	}
}
