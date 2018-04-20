package netty2;

import netty2.DiscardClientHandler;
import netty2.constants.CommonConstants;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

public class DisardClient {
	public static Bootstrap b;
	static PooledByteBufAllocator allocator = new PooledByteBufAllocator();
	static{
		try {
			EventLoopGroup workerGroup = new NioEventLoopGroup();
			b = new Bootstrap();
			b.group(workerGroup);
			b.channel(NioSocketChannel.class);
			b.option(ChannelOption.SO_KEEPALIVE, true);
			b.handler(new ChannelInitializer<SocketChannel>(){

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					// TODO Auto-generated method stub
					ch.pipeline().addLast(new DiscardClientHandler());
				}
			});
			//start client
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Object startClient(String obj) throws  Exception {
		ChannelFuture f = b.connect("localhost",8999).sync();;
//		f.channel().attr(AttributeKey.valueOf(CommonConstants.attribute_key)).set(obj);
		
		ByteBuf buf = allocator.buffer().writeBytes(obj.getBytes("utf-8"));
		
		f.channel().writeAndFlush(buf);
		f.channel().closeFuture().sync();
		return f.channel().attr(AttributeKey.valueOf(CommonConstants.attribute_key)).get();
	}

}
