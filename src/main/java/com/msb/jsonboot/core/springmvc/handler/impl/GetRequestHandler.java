package com.msb.jsonboot.core.springmvc.handler.impl;

import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.CharEncoding;
import org.apache.commons.codec.Charsets;

import com.msb.jsonboot.utils.UrlUtils;
import com.msb.jsonboot.entity.MethodDetail;
import com.msb.jsonboot.core.springmvc.factory.MethodDetailFactory;
import com.msb.jsonboot.core.springmvc.handler.RequestHandler;
import com.msb.jsonboot.core.springmvc.resolver.ParameterResolver;
import com.msb.jsonboot.core.springmvc.resolver.factory.ParameterResolverFactory;
import com.msb.jsonboot.utils.ReflectionUtil;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.QueryStringDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 * 处理GET请求
 * 
 * @author Stickerleee
 * @since 上午9:20:53
 */
@Slf4j
public class GetRequestHandler implements RequestHandler {

	@Override
	public Object handler(FullHttpRequest fullHttpRequest) {
		
        QueryStringDecoder queryDecoder = new QueryStringDecoder(fullHttpRequest.uri(), Charsets.toCharset(CharEncoding.UTF_8));
        //获取请求体中的参数列表
        Map<String, String> queryParamMap = UrlUtils.getQueryParam(queryDecoder);
        String path = queryDecoder.path();
        //TODO MethodFactory
        MethodDetail methodDetail = MethodDetailFactory.getMethodDetail(path, HttpMethod.GET);
        //若无数据或目标路径的未定义，返回null
        if (methodDetail == null || methodDetail.getMethod() == null) {
        	return null;
        }
        log.info("request path: {}, method: {}", path, methodDetail.getMethod().getName());
        //将GET请求体中的参数导入Detail中
        methodDetail.setQueryParameterMappings(queryParamMap);
        //通过反射获取该方法需要的参数
        Parameter[] methodParams = methodDetail.getMethod().getParameters();
        //最终调用的参数列表
        List<Object> params = new ArrayList<>();
        //根据注解调用对应方法
        for (Parameter parameter : methodParams) {
        	ParameterResolver parameterResolver = ParameterResolverFactory.get(parameter);
            Object result = parameterResolver.resolve(methodDetail, parameter);
            //依次导入参数值到列表中
            params.add(result);
        }

        return ReflectionUtil.executeMethod(methodDetail.getMethod(), params.toArray());
	}

}
