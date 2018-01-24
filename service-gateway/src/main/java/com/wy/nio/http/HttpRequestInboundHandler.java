package com.wy.nio.http;



import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpRequest;

/**
 * @author wy
 * @version 创建时间：2018年1月23日 上午10:17:50
 */
public class HttpRequestInboundHandler extends ChannelInboundHandlerAdapter {
	private HttpRequest request;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof HttpRequest) {
			request = (HttpRequest) msg;
			ctx.fireChannelRead(request);
		}
		if (msg instanceof HttpContent) {
			HttpContent content = (HttpContent) msg;
			ByteBuf buf = content.content();
			String json = buf.toString(io.netty.util.CharsetUtil.UTF_8);
			buf.release();
			String res = Distributor.getInstace().run(request, json);
			ctx.fireChannelRead(res);
		}
	}


	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		System.err.println(cause.getMessage());
		ctx.close();
	}
}
