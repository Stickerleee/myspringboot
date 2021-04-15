package com.msb.jsonboot.core.route;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.msb.jsonboot.annotation.GetMapping;
import com.msb.jsonboot.annotation.NewController;
import com.msb.jsonboot.annotation.PostMapping;
import com.msb.jsonboot.core.scanners.AnnotatedClassScanner;

/**
 * 将请求通过注解进行组合路由
 * 
 * @author Stickerleee
 * @since 上午9:31:32
 */
public class Route {
	
	/**
     * 类路径映射
     */
    public static Map<String, Class<?>> classMapping = new HashMap<>();

    /**
     * Get方法映射路径
     */
    public static Map<String, Method> getMethodMapping = new HashMap<>();

    /**
     * Post方法映射
     */
    public static Map<String, Method> postMethodMapping = new HashMap<>();
    
    public void loadRoutes(String packageName) {
    	AnnotatedClassScanner annotatedScanner = new AnnotatedClassScanner();
    	//获取扫描结果为一个包含目标注解的类的Set
    	Set<Class<?>> scan = annotatedScanner.scan(packageName, NewController.class);
    	for (Class<?> aClass : scan) {
    		NewController newController = aClass.getAnnotation(NewController.class);
    		String baseUri = newController.value();
    		//将结果导入类路径映射Map
    		classMapping.put(baseUri, aClass);
    		//通过反射，获取目标类中的所有方法
    		Method[] methods = aClass.getMethods();
    		loadMethodRoutes(baseUri, methods);
    	}
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
				String getMappingMethod = method.getAnnotation(GetMapping.class).value();
				//拼接Uri和方法作为key
				getMethodMapping.put(baseUri + getMappingMethod, method);
				continue;
			}
			if (method.isAnnotationPresent(PostMapping.class)) {
				String postMappingMethod = method.getAnnotation(PostMapping.class).value();
				postMethodMapping.put(baseUri + postMappingMethod, method);
			}
		}
	}
	
}
