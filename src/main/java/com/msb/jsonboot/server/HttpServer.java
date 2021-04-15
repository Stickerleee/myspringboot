package com.msb.jsonboot.server;

import com.msb.jsonboot.core.scanners.AnnotatedClassScanner;
import com.msb.jsonboot.common.SystemConstants;
import com.msb.jsonboot.server.HttpRequestHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 通过netty编写http服务器接收请求
 * 
 * @author Stickerleee
 * @since 上午9:10:16
 */
@Slf4j
public class HttpServer {

	private int port = 8080;
	
	/**
	 * 服务器启动方法
	 */
	public void run() {
		//初始化线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //服务端启动辅助类
            ServerBootstrap bootstrap = new ServerBootstrap();
            //置入线程组
            bootstrap.group(bossGroup, workerGroup)
            	.channel(NioServerSocketChannel.class)  //创建通道
            	//boss组定义日志输出形式
                .handler(new LoggingHandler(LogLevel.INFO))
            	.childHandler(new HttpServerInitializer() );  //创建责任链，处理接收的请求
            //服务启动辅助类的实例，将服务绑定到 8080 端口上
            ChannelFuture future = bootstrap.bind(this.port).sync();
            //服务器启动成功日志
            log.info(SystemConstants.LOG_PORT_BANNER, this.port);
            //等待服务端口关闭
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            // 退出，释放线程池资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            //服务器关闭日志
            log.info("Server in port: {} shut down successful", this.port);
        }
	}
	
	/**
	 * 责任链初始化
	 * 
	 */
	private class HttpServerInitializer extends ChannelInitializer<SocketChannel> {

		@Override
		protected void initChannel(SocketChannel ch) throws Exception {
			// TODO Auto-generated method stub
			ch.pipeline()
				//处理http消息的编解码
				.addLast("httpServerCodec", new HttpServerCodec())  
				//将请求转换为单一的 FullHttpReques
				.addLast("aggregator", new HttpObjectAggregator(512 * 1024)) 
				.addLast("handler", new HttpRequestHandler());  //处理请求
		}
		
	}
}
