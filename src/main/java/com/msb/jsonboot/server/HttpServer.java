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
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpServer {

	private int port = 8080;
	
	public void run() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //服务端启动辅助类
            ServerBootstrap bootstrap = new ServerBootstrap();
 
            bootstrap.group(bossGroup, workerGroup)
            	.channel(NioServerSocketChannel.class)  //创建通道
            	.childHandler(new HttpServerInitializer() );  //创建责任链，处理接收的请求
            //服务启动辅助类的实例，将服务绑定到 8080 端口上
            ChannelFuture future = bootstrap.bind(port).sync();
            log.info(SystemConstants.LOG_PORT_BANNER, this.port);
            //等待服务端口关闭
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            // 优雅退出，释放线程池资源
        	System.out.println("Server shutted dowm");
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
	}
	
	private class HttpServerInitializer extends ChannelInitializer<SocketChannel> {

		@Override
		protected void initChannel(SocketChannel ch) throws Exception {
			// TODO Auto-generated method stub
			ch.pipeline()
				.addLast("httpServerCodec", new HttpServerCodec())
				.addLast("aggregator", new HttpObjectAggregator(512 * 1024))
				.addLast("handler", new HttpRequestHandler());
		}
		
	}
}
