package com.msb.myspringboot.core.springmvc.handler;

import io.netty.handler.codec.http.FullHttpRequest;

public interface RequestHandler {
	/**
	 * 处理请求的接口
	 *
	 * @param fullHttpRequest 传入需要处理的请求信息
	 * @return 返回数据
	 */
	Object handler(FullHttpRequest fullHttpRequest);
}
