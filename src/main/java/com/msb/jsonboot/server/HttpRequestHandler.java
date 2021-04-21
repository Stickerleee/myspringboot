package com.msb.jsonboot.server;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.msb.jsonboot.core.springmvc.handler.RequestHandler;
import com.msb.jsonboot.core.springmvc.handler.impl.GetRequestHandler;
import com.msb.jsonboot.core.springmvc.handler.impl.PostRequestHandler;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.util.AsciiString;
import lombok.extern.slf4j.Slf4j;

/**
 * 用于处理netty服务器中的HTTP Request请求
 * 
 * @author Stickerleee
 * @since 上午9:12:55
 */
@Slf4j
public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

	/**
	 * 定义Map内容的具体类型
	 */
	private static final Map<HttpMethod, RequestHandler> REQUEST_HANDLER_MAP;

	//Map内容
    static {
        REQUEST_HANDLER_MAP = new HashMap<>();
        REQUEST_HANDLER_MAP.put(HttpMethod.GET, new GetRequestHandler());
        REQUEST_HANDLER_MAP.put(HttpMethod.POST, new PostRequestHandler());
    }
    //字符串常量
    private static final AsciiString CONNECTION = AsciiString.cached("Connection");
    private static final AsciiString KEEP_ALIVE = AsciiString.cached("keep-alive");
    
    //处理完请求后，给客户端发送数据
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        log.info("HttpRequestHandler complete");
        ctx.flush();
    }
	
    //处理接收到的Request
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
		String uri = msg.uri();
		if (StringUtils.isBlank(uri)) {
			return;
		}
        //根据请求的类型在map中取出对应的处理器
		RequestHandler requestHandler = REQUEST_HANDLER_MAP.get(msg.method());
        //对获得的数据进行相应处理
        FullHttpResponse response;
        try {
        	Object result = requestHandler.handler(msg);
        	response = HttpResponse.ok(result);
        }catch (Exception e) {
        	e.printStackTrace();
        	response = HttpResponse.internalServerError(uri);
        }
        boolean keepAlive = HttpUtil.isKeepAlive(msg);
        if (!keepAlive){
            //如果不是长链接，则写入数据后关闭此次channel连接
            ctx.write(response).addListener(ChannelFutureListener.CLOSE);
        }else{
            response.headers().set(CONNECTION, KEEP_ALIVE);
            ctx.write(response);
        }
	}
}
