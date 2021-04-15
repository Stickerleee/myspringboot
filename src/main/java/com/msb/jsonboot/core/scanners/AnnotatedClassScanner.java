package com.msb.jsonboot.core.scanners;

import java.lang.annotation.Annotation;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.TypeAnnotationsScanner;

import com.msb.jsonboot.core.scanners.AnnotatedClassScanner;

import lombok.extern.slf4j.Slf4j;

/**
 * 注解解析类
 * 
 * @author Stickerleee
 * @since 2021年4月15日 上午9:35:29
 */
@Slf4j
public class AnnotatedClassScanner {
	
    /**
     * 扫描参数路径内的注解
     *
     * @param packageName 需要扫描的包路径
     * @param annotation 需要在包路径内寻找类的注解
     * @return Set 包含路径内所有该注解的类
     */
	public Set<Class<?>> scan(String packageName, Class<? extends Annotation> annotation){
		//使用反射，获取注解对应的类
		Reflections reflections = new Reflections(packageName, new TypeAnnotationsScanner());
		Set<Class<?>> result = reflections.getTypesAnnotatedWith(annotation, true);
		log.info("annotationClass : {}, size: {}", result, result.size());
		return result;
		
	}
}
