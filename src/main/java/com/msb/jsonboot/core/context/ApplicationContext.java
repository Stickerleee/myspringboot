package com.msb.jsonboot.core.context;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import com.msb.jsonboot.core.context.ApplicationContext;
import com.msb.jsonboot.core.factory.*;
import com.msb.jsonboot.core.ioc.BeanFactory;

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
     * 获取单例
     *
     * @return 唯一的ApplicationContext对象
     */
    public static ApplicationContext getInstance(){
        return APPLICATION_CONTEXT;
    }
	
    /**
     * 注解相关映射 key=注解类型：value=含该注解类的集合
     */
    public static final Map<Class<? extends Annotation>, Set<Class<?>>> CLASSES = new HashMap<>();
    
    public String packageName = "com.msb";
    /**
     * 对目标包进行解析
     * @param packageName
     */
    public void run(String packageName) {
    	this.packageName = packageName;
    	ClassFactory.loadClass(packageName);
    	RouteFactory.loadRoutes();
    	BeanFactory.loadBeans();
    }
    
}
