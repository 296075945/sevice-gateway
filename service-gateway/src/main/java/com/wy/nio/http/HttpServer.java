package com.wy.nio.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
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
		
		ServerBootstrap bootstrap = new ServerBootstrap();
		
		bootstrap.group(new NioEventLoopGroup(16), new NioEventLoopGroup(64));
		bootstrap.channel(NioServerSocketChannel.class);
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
		bootstrap.bind(8090).sync();
	}
}
