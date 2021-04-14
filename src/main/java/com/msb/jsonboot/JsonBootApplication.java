package com.msb.jsonboot;

import com.msb.jsonboot.common.Banner;
import com.msb.jsonboot.common.JsonBootBanner;
import com.msb.jsonboot.core.route.Route;
import com.msb.jsonboot.server.HttpServer;

/**
 * jsonboot 启动类
 *
 */

public class JsonBootApplication {
	public static void main(String[] args) {
		Banner banner = new JsonBootBanner();
		banner.printBanner(null, System.out);
//		Route route = new Route();
//		route.loadRoutes("com.msb.demo");
		HttpServer httpServer = new HttpServer();
		httpServer.run();
	}
}
