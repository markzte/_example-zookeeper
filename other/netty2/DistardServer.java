package netty2;

import netty2.MyChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class DistardServer {
	int port;

	public DistardServer(int port) {
		// TODO Auto-generated constructor stub
		this.port = port;
	}
	
	public void run()throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workGroup = new NioEventLoopGroup();
		
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup,workGroup).channel(NioServerSocketChannel.class).childHandler(new MyChannelInitializer())
			.option(ChannelOption.SO_BACKLOG,128)
			.childOption(ChannelOption.SO_KEEPALIVE,true);
			ChannelFuture f = b.bind(port).sync();
			f.channel().closeFuture().sync();
		} finally{
			// TODO Auto-generated catch block
			workGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}
	
	public static void main(String[] args) throws Exception {
		int port = 8999;
		new DistardServer(port).run();
	}
}
