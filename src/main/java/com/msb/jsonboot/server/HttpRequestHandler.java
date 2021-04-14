package com.msb.jsonboot.server;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.msb.jsonboot.core.scanners.AnnotatedClassScanner;
import com.msb.jsonboot.handler.RequestHandler;
import com.msb.jsonboot.handler.impl.GetRequestHandler;
import com.msb.jsonboot.handler.impl.PostRequestHandler;
import com.msb.jsonboot.serialize.impl.JacksonSerializer;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.AsciiString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

	private static final Map<HttpMethod, RequestHandler> REQUEST_HANDLER_MAP;
	private static final AsciiString CONTENT_TYPE = AsciiString.cached("Content-Type");

    static {
        REQUEST_HANDLER_MAP = new HashMap<>();
        REQUEST_HANDLER_MAP.put(HttpMethod.GET, new GetRequestHandler());
        REQUEST_HANDLER_MAP.put(HttpMethod.POST, new PostRequestHandler());
    }
    
    private static final AsciiString CONTENT_LENGTH = AsciiString.cached("Content-Length");
    private static final AsciiString CONNECTION = AsciiString.cached("Connection");
    private static final AsciiString KEEP_ALIVE = AsciiString.cached("keep-alive");
    private static final String APPLICATION_JSON = "application/json";
    
    //发送数据
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        log.info("flush HttpResponse");
        ctx.flush();
    }
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
		String uri = msg.uri();
		if (StringUtils.isBlank(uri)) {
			return;
		}
        //根据请求的类型在map中取出对应的处理器
		RequestHandler requestHandler = REQUEST_HANDLER_MAP.get(msg.method());
        Object result = requestHandler.handler(msg);
        //对获得的数据进行相应处理
        FullHttpResponse response = buildHttpResponse(result);
        boolean keepAlive = HttpUtil.isKeepAlive(msg);
        if (!keepAlive){
            //如果不是长链接，则写入数据后关闭此次channel连接
            ctx.write(response).addListener(ChannelFutureListener.CLOSE);
        }else{
            response.headers().set(CONNECTION, KEEP_ALIVE);
            ctx.write(response);
        }
	}

    /**
     * 对请求处理的结果进行一个HTTP的封装
     *
     * @param result 请求处理后得到的数据结果
     * @return 封装好的响应
     */
	private FullHttpResponse buildHttpResponse(Object result) {
		JacksonSerializer jacksonSerializer = new JacksonSerializer();
        byte[] bytes = jacksonSerializer.serialize(result);
        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer(bytes));
        response.headers().set(CONTENT_TYPE, APPLICATION_JSON);
        response.headers().set(CONTENT_LENGTH, response.content().readableBytes());
        log.info("build HttpResponse Successful");
        return response;
	}

}
