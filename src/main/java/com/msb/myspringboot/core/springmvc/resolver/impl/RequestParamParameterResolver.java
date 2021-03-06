package com.msb.myspringboot.core.springmvc.resolver.impl;

import java.lang.reflect.Parameter;

import com.msb.myspringboot.annotation.springmvc.RequestParam;
import com.msb.myspringboot.core.springmvc.resolver.ParameterResolver;
import com.msb.myspringboot.entity.MethodDetail;
import com.msb.myspringboot.utils.ObjectUtils;

/**
 * RequestParam 注解的实现
 * 
 * @author Stickerleee
 * @since 2021年4月16日 下午3:26:39
 */
public class RequestParamParameterResolver implements ParameterResolver {

	@Override
	public Object resolve(MethodDetail methodDetail, Parameter parameter) {
		RequestParam requestParam = parameter.getAnnotation(RequestParam.class);
		// 获取每个参数的类型
		Class<?> type = parameter.getType();
		// 获取注解设置的value值
		String paramKey = requestParam.value();
		// 以注解设置的value值为key在参数列表中取出对应值
		String paramValue = methodDetail.getQueryParameterMappings().get(paramKey);
		return ObjectUtils.convertToClass(type, paramValue);
	}

}
