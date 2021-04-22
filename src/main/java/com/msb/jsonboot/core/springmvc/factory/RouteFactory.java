/**
 * 
 */
package com.msb.jsonboot.core.springmvc.factory;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import com.msb.jsonboot.annotation.springmvc.*;
import com.msb.jsonboot.core.context.ApplicationContext;
import com.msb.jsonboot.entity.MethodDetail;
import com.msb.jsonboot.utils.UrlUtils;

import io.netty.handler.codec.http.HttpMethod;

/**
 * 将请求通过注解进行组合路由
 * 
 * @author Stickerleee
 * @since 2021年4月17日 上午10:48:47
 */
public class RouteFactory {

    /**
     * Get方法Map key=总路径：value=目标请求方法
     */
    public static Map<String, Method> getMethodMapping = new HashMap<>();

    /**
     * Post方法Map key=总路径：value=目标请求方法
     */
    public static Map<String, Method> postMethodMapping = new HashMap<>();
    
    /**
     * Get方法Map key=用正则表达式替换过的Url路径：value=原url
     */
    public static Map<String, String> getUrlMapping = new HashMap<>();

    /**
     * Post方法Map key=用正则表达式替换过的Url路径：value=原url
     */
    public static Map<String, String> postUrlMapping = new HashMap<>();

    /**
     * Get方法Map 用正则表达式替换过的Url路径 : MethodDetail
     */
    public static Map<String, MethodDetail> getMethodDetailMapping = new HashMap<>();

    /**
     * Post方法Map 用正则表达式替换过的Url路径 : MethodDetail
     */
    public static Map<String, MethodDetail> postMethodDetailMapping = new HashMap<>();
    
    /**
     * 导入RestController标注类下的方法到Map
     * @param packageName 目标包名
     */
    public static void loadRoutes() {
    	
    	Set<Class<?>> classes = ApplicationContext.CLASSES.get(RestController.class);
    	for (Class<?> aClass : classes) {
    		RestController restController = aClass.getAnnotation(RestController.class);
    		String baseUri = restController.value();
    		//通过反射，获取目标类中的所有方法
    		Method[] methods = aClass.getMethods();
    		loadMethodRoutes(baseUri, methods);
    	}
    	System.out.println("GET POST METHOD");
        System.out.println(getMethodMapping);
        System.out.println(postMethodMapping);
    }

	/**
	 * 导入GET、POST方法到对应的Map
	 * 
	 * @param baseUri 目标类的uri路径
	 * @param methods 目标类中的方法数列
	 */
	private static void loadMethodRoutes(String baseUri, Method[] methods) {
		for (Method method : methods) {
			if (method.isAnnotationPresent(GetMapping.class)) {
				//获取get方法的目标路径
				String getMappingName = method.getAnnotation(GetMapping.class).value();
				//拼接父路径和子路径
				String url = baseUri + getMappingName;
				String formatUrl = UrlUtils.formatUrl(url);
				getMethodMapping.put(formatUrl, method);
				getUrlMapping.put(formatUrl, url);
				continue;
			}
			if (method.isAnnotationPresent(PostMapping.class)) {
				//同上
				String postMappingName = method.getAnnotation(PostMapping.class).value();
				String url = baseUri + postMappingName;
				String formatUrl = UrlUtils.formatUrl(url);
				postMethodMapping.put(formatUrl, method);
				postUrlMapping.put(formatUrl, url);
				continue;
			}
		}
	}
	
    /**
     * 根据请求方法参数化path
     *
     * @param requestPath 请求path 如 /user/xxx
     * @param httpMethod 请求的方法类型
     * @return MethodDetail
     */
    public MethodDetail getMethodDetail(String requestPath, HttpMethod httpMethod){
        MethodDetail methodDetail = null;
        if (HttpMethod.GET.equals(httpMethod)){
            methodDetail = buildMethodDetail(requestPath, getMethodMapping, getUrlMapping, getMethodDetailMapping);
        }
        if (HttpMethod.POST.equals(httpMethod)){
            methodDetail = buildMethodDetail(requestPath, postMethodMapping, postUrlMapping, postMethodDetailMapping);
        }
        return methodDetail;
    }

    /**
     * 生成MethodDetail
     *
     * @param requestPath 请求路径path
     * @param getMethodMapping formatUrl:method
     * @param getUrlMapping formatUrl:url
     * @param getMethodDetailMapping formatUrl:methodDetail
     * @return MethodDetail
     */
    private MethodDetail buildMethodDetail(String requestPath, Map<String, Method> getMethodMapping
                                                , Map<String, String> getUrlMapping, Map<String, MethodDetail> getMethodDetailMapping){
        MethodDetail methodDetail = new MethodDetail();
        //遍历formatUrl与Method
        getMethodMapping.forEach((key, value) -> {
            //使用之前定义的正则，来判断path是否符合该路径
            Pattern pattern = Pattern.compile(key);
            if (pattern.matcher(requestPath).find()){
                methodDetail.setMethod(value);
                //还原url
                String url = getUrlMapping.get(key);
                Map<String, String> urlParameterMappings = UrlUtils.getUrlParameterMappings(requestPath, url);
                //为本次请求设置一个参数Map
                methodDetail.setUrlParameterMappings(urlParameterMappings);
                getMethodDetailMapping.put(key, methodDetail);
            }
        });
        return methodDetail;
    }

}
