package com.msb.jsonboot;

import com.msb.jsonboot.common.Banner;
import com.msb.jsonboot.common.JsonBootBanner;
import com.msb.jsonboot.core.context.ApplicationContext;
import com.msb.jsonboot.server.HttpServer;

/**
 * jsonboot 启动类
 * 
 * @author Stickerleee
 * @since 上午9:31:24
 */

public class JsonBootApplication {
	public static void main(String[] args) {
		Banner banner = new JsonBootBanner();
		banner.printBanner(null, System.out);
		
		ApplicationContext applicationContext = new ApplicationContext();
		applicationContext.run("com.msb.demo");
		
		HttpServer httpServer = new HttpServer();
		httpServer.run();
	}
}