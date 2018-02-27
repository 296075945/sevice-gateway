package com.wy.nio.http;

import java.text.ParseException;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

/**
 * @author wy
 * @version 创建时间：2018年1月23日 上午10:10:33
 */
public class HttpServer {
	public static void main(String[] args) throws InterruptedException {
		
		int port = 8090;
		try{
			port = Integer.parseInt(args[0]);
		}catch(Exception e){
			e.printStackTrace();
		}
		boolean isLinux = isLinux();
		ServerBootstrap bootstrap = new ServerBootstrap();
		EventLoopGroup loopGroup = isLinux?new EpollEventLoopGroup():new NioEventLoopGroup();
		bootstrap.group(loopGroup, loopGroup);
		bootstrap.channel(isLinux?EpollServerSocketChannel.class:NioServerSocketChannel.class);
		bootstrap.childHandler(new ChannelInitializer<SocketChannel>(){
			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				
				ChannelPipeline pipeline = ch.pipeline();
				pipeline.addLast(new HttpResponseEncoder());//4
				pipeline.addLast(new HttpRequestDecoder()); //1
				pipeline.addLast(new HttpRequestInboundHandler());//2
				pipeline.addLast(new HttpResponseInboundHandler());//3
				
			}
		});
		bootstrap.option(ChannelOption.SO_BACKLOG, 128);
		bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
		System.out.println("服务已启动 端口:"+port);
		bootstrap.bind(port).sync();
	}
	private static boolean isLinux(){
		String os = System.getProperty("os.name", "");
		return os.toLowerCase().indexOf("linux") > -1;
	}
}
