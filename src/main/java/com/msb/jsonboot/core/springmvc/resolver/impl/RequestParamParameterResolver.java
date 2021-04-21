package com.msb.jsonboot.core.springmvc.resolver.impl;

import com.msb.jsonboot.annotation.springmvc.RequestParam;
import com.msb.jsonboot.core.springmvc.resolver.ParameterResolver;
import com.msb.jsonboot.entity.MethodDetail;
import com.msb.jsonboot.utils.ObjectUtils;

import java.lang.reflect.Parameter;

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
        //获取每个参数的类型
        Class<?> type = parameter.getType();
        //获取注解设置的value值
        String paramKey = requestParam.value();
        //以注解设置的value值为key在参数列表中取出对应值
        String paramValue = methodDetail.getQueryParameterMappings().get(paramKey);
        return ObjectUtils.convertToClass(type, paramValue);
    }

}
