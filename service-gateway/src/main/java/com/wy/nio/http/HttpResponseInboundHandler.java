package com.wy.nio.http;



import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;

/**
 * @author wy
 * @version 创建时间：2018年1月23日 下午4:19:16
 */
public class HttpResponseInboundHandler extends ChannelInboundHandlerAdapter{
	private HttpRequest request;
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception{
		
		if (msg instanceof HttpRequest){
			request = (HttpRequest) msg;
		}
		if(msg instanceof String){
			FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, 
					HttpResponseStatus.OK,
					Unpooled.wrappedBuffer(msg.toString().getBytes("UTF-8")));
			response.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json");
			response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
			if (HttpUtil.isKeepAlive(request)) {
				response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
			}
			
			ctx.write(response);
		}
	}
	@Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
