package com.msb.jsonboot.handler.impl;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.CharEncoding;
import org.apache.commons.codec.Charsets;

import com.msb.jsonboot.utils.UrlUtils;
import com.msb.jsonboot.utils.ObjectUtils;
import com.msb.jsonboot.entity.MethodDetail;
import com.msb.jsonboot.core.resolver.ParameterResolver;
import com.msb.jsonboot.core.resolver.factory.ParameterResolverFactory;
import com.msb.jsonboot.utils.ReflectionUtil;
import com.msb.jsonboot.annotation.RequestParam;
import com.msb.jsonboot.core.context.ApplicationContext;
import com.msb.jsonboot.core.scanners.AnnotatedClassScanner;
import com.msb.jsonboot.handler.RequestHandler;

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
		//解析GET请求
        QueryStringDecoder queryDecoder = new QueryStringDecoder(fullHttpRequest.uri(), Charsets.toCharset(CharEncoding.UTF_8));
        Map<String, String> queryParamMap = UrlUtils.getQueryParam(queryDecoder);
        String path = queryDecoder.path();
        //获取应用唯一的实例
        ApplicationContext applicationContext = ApplicationContext.getInstance();
        MethodDetail methodDetail = applicationContext.getMethodDetail(path, HttpMethod.GET);
        //若未定义目标路径，返回null
        if (methodDetail == null || methodDetail.getMethod() == null) {
        	return null;
        }
        log.info("request path: {}, mthod: {}", path, methodDetail.getMethod().getName());
        methodDetail.setQueryParameterMappings(queryParamMap);
        //通过反射获取该方法需要的参数
        Parameter[] methodParams = methodDetail.getMethod().getParameters();
        //最终调用的参数列表
        List<Object> params = new ArrayList<>();
        //根据注解调用对应方法
        for (Parameter parameter : methodParams) {
        	ParameterResolver parameterResolver = ParameterResolverFactory.get(parameter);
            Object result = parameterResolver.resolve(methodDetail, parameter);
            params.add(result);
        }

        return ReflectionUtil.executeMethod(methodDetail.getMethod(), params.toArray());
	}

}
