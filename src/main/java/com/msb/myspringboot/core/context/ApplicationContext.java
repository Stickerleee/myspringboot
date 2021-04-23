package com.msb.myspringboot.core.context;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.msb.myspringboot.annotation.boot.ComponentScan;
import com.msb.myspringboot.common.Banner;
import com.msb.myspringboot.common.MyBootBanner;
import com.msb.myspringboot.core.ioc.BeanFactory;
import com.msb.myspringboot.core.springmvc.factory.ClassFactory;
import com.msb.myspringboot.core.springmvc.factory.RouteFactory;
import com.msb.myspringboot.server.HttpServer;

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
	public static ApplicationContext getInstance() {
		return APPLICATION_CONTEXT;
	}

	/**
	 * 注解相关映射 key=注解类型：value=含该注解类的集合
	 */
	public static final Map<Class<? extends Annotation>, Set<Class<?>>> CLASSES = new HashMap<>();

	/**
	 * 存储启动路径的数列
	 */
	private String[] packageNames;

	private void setPackageName(String[] packageNames) {
		this.packageNames = packageNames;
	}

	public String[] getPackageNames() {
		return packageNames;
	}

	/**
	 * 启动程序
	 * 
	 * @param myBootClass
	 */
	public void run(Class<?> myBootClass) {

		String[] packageNames = analysisPackageNames(myBootClass);
		setPackageName(packageNames);
		// 打印Banner
		Banner banner = new MyBootBanner();
		banner.printBanner(null, System.out);
		// 初始化Bean
		ClassFactory.loadClass();
		RouteFactory.loadRoutes();
		BeanFactory.loadBeans();
		// 启动Netty服务器
		HttpServer httpServer = new HttpServer();
		httpServer.run();
	}

	/**
	 * 通过解析@ComponentScan注解获取路径位置
	 * 
	 * 若@ComponentScan没有值或没有标注，则使用启动类的位置作为初始路径
	 * 
	 * @param myBootClass
	 * @return 包名数组
	 */
	private String[] analysisPackageNames(Class<?> myBootClass) {
		ComponentScan componentScan = myBootClass.getAnnotation(ComponentScan.class);
		if (Objects.isNull(componentScan) || StringUtils.isBlank(componentScan.value()[0])) {
			System.out.println("@componentScan is not existed or has no value");
			return new String[] { myBootClass.getPackage().getName() };
		} else {
			return componentScan.value();
		}
	}

}
