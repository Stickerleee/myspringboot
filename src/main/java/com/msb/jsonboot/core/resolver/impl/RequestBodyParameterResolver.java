package com.msb.jsonboot.core.resolver.impl;

import com.msb.jsonboot.core.resolver.ParameterResolver;
import com.msb.jsonboot.entity.MethodDetail;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.Parameter;

/**
 * 解析RequestBody注解的参数
 * 
 * @author Stickerleee
 * @since 2021年4月16日 下午3:26:48
 */
public class RequestBodyParameterResolver implements ParameterResolver {

    /**
     * 转换格式
     */
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Object resolve(MethodDetail methodDetail, Parameter parameter) {
        Object result = null;
        try {
        	//对请求中的BODY进行读取
            result = objectMapper.readValue(methodDetail.getJson(), parameter.getType());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }

}
