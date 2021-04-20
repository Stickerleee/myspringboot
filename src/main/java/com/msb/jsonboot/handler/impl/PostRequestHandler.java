package com.msb.jsonboot.handler.impl;

import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.CharEncoding;
import org.apache.commons.codec.Charsets;
import org.apache.commons.lang3.StringUtils;

import com.msb.jsonboot.core.factory.MethodDetailFactory;
import com.msb.jsonboot.core.resolver.ParameterResolver;
import com.msb.jsonboot.core.resolver.factory.ParameterResolverFactory;
import com.msb.jsonboot.entity.MethodDetail;
import com.msb.jsonboot.utils.ReflectionUtil;
import com.msb.jsonboot.handler.RequestHandler;
import com.msb.jsonboot.utils.UrlUtils;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.QueryStringDecoder;

/**
 * 处理POST请求
 * 
 * @author Stickerleee
 * @since 上午9:31:09
 */
public class PostRequestHandler implements RequestHandler {

    /**
     * 代表http内容类型格式
     */
    private static final String CONTENT_TYPE = "Content-Type";

    /**
     * json的内容格式
     */
    private static final String APPLICATION_JSON = "application/json";
    
    /**
     * 表单的内容格式  之后补充
     */
    private static final String FORM_URLENCODED= "application/x-www-form-urlencoded";

    @Override
    public Object handler(FullHttpRequest fullHttpRequest) {
    	
    	 QueryStringDecoder queryDecoder = new QueryStringDecoder(fullHttpRequest.uri(), Charsets.toCharset(CharEncoding.UTF_8));
         //获取POST请求体中的参数列表
         Map<String, String> queryParamMap = UrlUtils.getQueryParam(queryDecoder);
         String path = queryDecoder.path();
         //根据映射决定参数列表的处理方法
         MethodDetail methodDetail = MethodDetailFactory.getMethodDetail(path, HttpMethod.POST);
         if (methodDetail == null || methodDetail.getMethod() == null){
             return null;
         }
         methodDetail.setQueryParameterMappings(queryParamMap);
         Parameter[] parameters = methodDetail.getMethod().getParameters();
         //获取post请求提交的内容
         String contentTypeStr = fullHttpRequest.headers().get(CONTENT_TYPE);
         if (StringUtils.isBlank(contentTypeStr)){
             return null;
         }
         String contentType = contentTypeStr.split(";")[0];
         //最终调用参数列表
         List<Object> params = new ArrayList<>();
         //设置传入的参数实体
         String jsonContent = fullHttpRequest.content().toString(Charsets.toCharset(CharEncoding.UTF_8));
         methodDetail.setJson(jsonContent);
         //如果是json格式  改造结构 在handler下设置annotation包 用作处理
         if (StringUtils.equals(APPLICATION_JSON, contentType)){
             for(Parameter parameter : parameters){
                 ParameterResolver parameterResolver = ParameterResolverFactory.get(parameter);
                 Object result = parameterResolver.resolve(methodDetail, parameter);
                 params.add(result);
             }
         }
         return ReflectionUtil.executeMethod(methodDetail.getMethod(), params.toArray());
    }

}
