package com.msb.jsonboot.core.springmvc.resolver.impl;

import com.msb.jsonboot.annotation.springmvc.PathVariable;
import com.msb.jsonboot.core.springmvc.resolver.ParameterResolver;
import com.msb.jsonboot.entity.MethodDetail;
import com.msb.jsonboot.utils.ObjectUtils;

import java.lang.reflect.Parameter;

/**
 * 解析PathVariable注解
 * 
 * @author Stickerleee
 * @since 2021年4月16日 下午3:27:29
 */
public class PathVariableParameterResolver implements ParameterResolver {

    @Override
    public Object resolve(MethodDetail methodDetail, Parameter parameter) {
        PathVariable pathVariable = parameter.getAnnotation(PathVariable.class);
        //获取每个参数的类型
        Class<?> type = parameter.getType();
        //获取注解的值
        String mappingKey = pathVariable.value();
        //从Url参数列表中取出对应值
        String value = methodDetail.getUrlParameterMappings().get(mappingKey);
        return ObjectUtils.convertToClass(type, value);
    }
}
