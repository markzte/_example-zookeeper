package netty2;

import java.io.UnsupportedEncodingException;

import netty2.constants.CommonConstants;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.cors.CorsConfig.Builder;
import io.netty.util.AttributeKey;

/**
 * Handles a client-side channel.
 */
public class DiscardClientHandler extends SimpleChannelInboundHandler<Object> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
//    	String req = (String) ctx.channel().attr(AttributeKey.valueOf(CommonConstants.attribute_key)).get();
//		try {
//			System.out.println("请求数据为："+req);
//			ByteBuf	buf = ctx.alloc().buffer().writeBytes(req.getBytes("utf-8"));
//			ctx.writeAndFlush(buf);
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
    }
    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
    		throws Exception {
    	// TODO Auto-generated method stub
    	ByteBuf buf = (ByteBuf)msg;
    	StringBuilder build = new StringBuilder();
    	
    	while(buf.isReadable()){
    		build.append((char) buf.readByte());
    		System.out.flush();
    	}
    	ctx.channel().attr(AttributeKey.valueOf(CommonConstants.attribute_key)).set(build.toString());
    	ctx.channel().close();
    	ctx.close();
    }

	@Override
	protected void channelRead0(ChannelHandlerContext arg0, Object arg1)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
 
}
