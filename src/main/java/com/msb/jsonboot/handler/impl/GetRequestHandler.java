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
import com.msb.jsonboot.annotation.RequestParam;
import com.msb.jsonboot.core.route.Route;
import com.msb.jsonboot.core.scanners.AnnotatedClassScanner;
import com.msb.jsonboot.handler.RequestHandler;

import io.netty.handler.codec.http.FullHttpRequest;
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
        //根据GET请求的路径，从路由Map中取出对应的渲染方法
        Method method = Route.getMethodMapping.get(path);
        if (method == null) {
        	return null;
        }
        log.info("request path: {}, method: {}", path, method.getName());
        //通过反射获取该方法需要的参数
        Parameter[] methodParams = method.getParameters();
        //最终调用的参数列表
        List<Object> params = new ArrayList<>();
        for (Parameter parameter : methodParams) {
        	RequestParam requestParam = parameter.getAnnotation(RequestParam.class);
        	//获取该参数的类型
        	Class<?> type = parameter.getType();
        	//最终参数的值
        	String paramValue;
        	//有注解时
        	if (requestParam != null) {
        		//将注解设置的value值作为参数的key值
        		String paramKey = requestParam.value();
        		//在url中取出对应的值
        		paramValue = queryParamMap.get(paramKey);
        	} else {
        		//无注解时,把参数作为key值查找对应值
        		paramValue = queryParamMap.get(parameter.getName());
        	}
        	params.add(ObjectUtils.convertToClass(type, paramValue));
        }

        return null;
	}

}
