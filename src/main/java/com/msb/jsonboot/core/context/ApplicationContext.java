package com.msb.jsonboot.core.context;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import com.msb.jsonboot.entity.MethodDetail;
import com.msb.jsonboot.utils.UrlUtils;

import io.netty.handler.codec.http.HttpMethod;

import com.msb.jsonboot.core.context.ApplicationContext;
import com.msb.jsonboot.annotation.*;
import com.msb.jsonboot.core.scanners.AnnotatedClassScanner;

/**
 * 将请求通过注解进行组合路由
 * 
 * @author Stickerleee
 * @since 上午9:31:32
 */
public class ApplicationContext {
	
	/**
     * 单例模式
     */
    private static final ApplicationContext APPLICATION_CONTEXT = new ApplicationContext();
    
	/**
     * 类路径映射 父路径：类
     */
    public static Map<String, Class<?>> classMapping = new HashMap<>();

    /**
     * Get方法映射路径 总路径：目标方法
     */
    public static Map<String, Method> getMethodMapping = new HashMap<>();

    /**
     * Post方法映射 总路径：目标方法
     */
    public static Map<String, Method> postMethodMapping = new HashMap<>();
    
    /**
     * Get方法映射 getFormatUrl（用正则表达式替换过的Url路径）：:原url
     */
    public static Map<String, String> getUrlMapping = new HashMap<>();

    /**
     * Post方法映射 postFormatUrl（用正则表达式替换过的Url路径）：原url
     */
    public static Map<String, String> postUrlMapping = new HashMap<>();

    /**
     * Get方法 存放getFormatUrl : MethodDetail
     */
    public static Map<String, MethodDetail> getMethodDetailMapping = new HashMap<>();

    /**
     * Post方法 存放postFormatUrl : MethodDetail
     */
    public static Map<String, MethodDetail> postMethodDetailMapping = new HashMap<>();
    
    public void loadRoutes(String packageName) {
    	AnnotatedClassScanner annotatedScanner = new AnnotatedClassScanner();
    	//获取扫描结果为一个包含目标注解的类的Set
    	Set<Class<?>> scan = annotatedScanner.scan(packageName, RestController.class);
    	for (Class<?> aClass : scan) {
    		RestController newController = aClass.getAnnotation(RestController.class);
    		String baseUri = newController.value();
    		//将结果导入类路径映射Map
    		classMapping.put(baseUri, aClass);
    		//通过反射，获取目标类中的所有方法
    		Method[] methods = aClass.getMethods();
    		loadMethodRoutes(baseUri, methods);
    	}
        System.out.println(classMapping);
        System.out.println(getMethodMapping);
        System.out.println(postMethodMapping);
    }

	/**
	 * 将类中含GET和POST注解的方法导入对应的映射Map
	 * 
	 * @param baseUri 目标类的uri路径
	 * @param methods 目标类中的方法数列
	 */
	private void loadMethodRoutes(String baseUri, Method[] methods) {
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
			}
		}
	}
	
    /**
     * 获取请求对应的MethodDetail
     *
     * @param requestPath 请求path 如 /user/xxx
     * @param httpMethod 请求的方法类型 如 get、post
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
     * @param getMethodMapping 存放方法的mapping映射 formatUrl:method
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
	
    /**
     * 获取实例对象
     *
     * @return 唯一的ApplicationContext对象
     */
    public static ApplicationContext getInstance(){
        return APPLICATION_CONTEXT;
    }
	
}
