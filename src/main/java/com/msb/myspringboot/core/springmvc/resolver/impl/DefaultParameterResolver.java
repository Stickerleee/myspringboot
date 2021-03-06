package com.msb.myspringboot.core.springmvc.resolver.impl;

import java.lang.reflect.Parameter;

import com.msb.myspringboot.core.springmvc.resolver.ParameterResolver;
import com.msb.myspringboot.entity.MethodDetail;
import com.msb.myspringboot.utils.ObjectUtils;

/**
 * 如果parameter上没有任何注解，就使用默认的parameter处理方式
 * 
 * @author Stickerleee
 * @since 2021年4月16日 下午4:02:42
 */
public class DefaultParameterResolver implements ParameterResolver {

	@Override
	public Object resolve(MethodDetail methodDetail, Parameter parameter) {
		// 获取每个参数的类型
		Class<?> type = parameter.getType();
		// 如果没有注解，则直接使用名称进行对应的查找
		String paramValue = methodDetail.getQueryParameterMappings().get(parameter.getName());
		return ObjectUtils.convertToClass(type, paramValue);
	}
}
