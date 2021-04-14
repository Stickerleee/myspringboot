package com.msb.jsonboot.core.route;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.msb.jsonboot.annotation.RestController;
import com.msb.jsonboot.core.scanners.AnnotatedClassScanner;

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
    
    public void loadRouters(String packageName) {
    	//引入搜索工具类
    	AnnotatedClassScanner annotatedScanner = new AnnotatedClassScanner();
    	//获取扫描结果
    	Set<Class<?>> scan = annotatedScanner.scan(packageName, RestController.class);
    	
    }
}
