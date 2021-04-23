package com.msb.myspringboot.core.springmvc.factory;

import static com.msb.myspringboot.core.springmvc.factory.RouteFactory.getMethodMapping;
import static com.msb.myspringboot.core.springmvc.factory.RouteFactory.getUrlMapping;
import static com.msb.myspringboot.core.springmvc.factory.RouteFactory.postMethodMapping;
import static com.msb.myspringboot.core.springmvc.factory.RouteFactory.postUrlMapping;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.regex.Pattern;

import com.msb.myspringboot.entity.MethodDetail;
import com.msb.myspringboot.utils.UrlUtils;

import io.netty.handler.codec.http.HttpMethod;

/**
 * @author Stickerleee
 * @since 2021年4月17日 下午3:04:30
 */
public class MethodDetailFactory {
	/**
	 * 获取请求的MethodDetail
	 *
	 * @param requestPath 请求path 如 /user/xxx
	 * @param httpMethod  请求的方法类型 如 get、post
	 * @return MethodDetail
	 */
	public static MethodDetail getMethodDetail(String requestPath, HttpMethod httpMethod) {
		MethodDetail methodDetail = null;
		if (HttpMethod.GET.equals(httpMethod)) {
			methodDetail = buildMethodDetail(requestPath, getMethodMapping, getUrlMapping);
		}
		if (HttpMethod.POST.equals(httpMethod)) {
			methodDetail = buildMethodDetail(requestPath, postMethodMapping, postUrlMapping);
		}
		return methodDetail;
	}

	/**
	 * 生成MethodDetail
	 *
	 * @param requestPath      请求路径path
	 * @param getMethodMapping 存放方法的mapping映射 formatUrl:method
	 * @param getUrlMapping    formatUrl:url
	 * @return MethodDetail
	 */
	private static MethodDetail buildMethodDetail(String requestPath, Map<String, Method> getMethodMapping,
			Map<String, String> getUrlMapping) {
		MethodDetail methodDetail = new MethodDetail();
		// 遍历formatUrl与Method
		getMethodMapping.forEach((key, value) -> {
			// 使用之前定义的正则，来判断是否是一个path
			Pattern pattern = Pattern.compile(key);
			if (pattern.matcher(requestPath).find()) {
				methodDetail.setMethod(value);
				// 获取到注解拼成到url
				String url = getUrlMapping.get(key);
				Map<String, String> urlParameterMappings = UrlUtils.getUrlParameterMappings(requestPath, url);
				methodDetail.setUrlParameterMappings(urlParameterMappings);
			}
		});
		return methodDetail;
	}
}
