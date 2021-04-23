package com.msb.myspringboot;

import com.msb.myspringboot.core.context.ApplicationContext;

/**
 * myspringboot 启动类
 * 
 * @author Stickerleee
 * @since 上午9:31:24
 */

public class MyBootApplication {
	/**
	 * 根据路径启动服务器
	 * 
	 * @param jsonBootClass
	 * @param args
	 */
	public static void run(Class<?> myBootClass, String... args) {

		ApplicationContext applicationContext = ApplicationContext.getInstance();
		applicationContext.run(myBootClass);

	}
}