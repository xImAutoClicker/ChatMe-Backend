package de.fhdw.nohn.cm.backed.network.server;

import de.fhdw.nohn.cm.backed.network.netty.MessageDeserializer;
import de.fhdw.nohn.cm.backed.network.netty.MessageLengthDeserializer;
import de.fhdw.nohn.cm.backed.network.netty.MessageLengthSerializer;
import de.fhdw.nohn.cm.backed.network.netty.MessageSerializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.ServerChannel;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.Getter;
import lombok.Setter;

@Getter
public class NettyChatServer extends Thread {

	private final int port;

	@Setter
	private Channel channel;
	
	public NettyChatServer(int port) {
		super("NettyChatServer");

		this.port = port;
	}

	/**
	 * Epoll.isAvailable() ? Linux : Windows
	 */
	@Override
	public void run() {
		System.out.println("Starting ChatServer on Port " + this.port + "...");
		
		EventLoopGroup bossGroup = Epoll.isAvailable() ? new EpollEventLoopGroup() : new NioEventLoopGroup();
		EventLoopGroup workerGroup = Epoll.isAvailable() ? new EpollEventLoopGroup() : new NioEventLoopGroup();
		
		try {
			Class<? extends ServerChannel> serverSocketChannelClass = Epoll.isAvailable()
					? EpollServerSocketChannel.class
					: NioServerSocketChannel.class;
			
			ServerBootstrap serverBootstrap = new ServerBootstrap();
			serverBootstrap.group(bossGroup, workerGroup).channel(serverSocketChannelClass)
					.childHandler(new ChannelInitializer<SocketChannel>() {

						@Override
						protected void initChannel(SocketChannel socketChannel) throws Exception {
							socketChannel.pipeline().addLast("splitter", new MessageLengthDeserializer());
							socketChannel.pipeline().addLast("decoder", new MessageDeserializer());
							socketChannel.pipeline().addLast("prepender", new MessageLengthSerializer());
							socketChannel.pipeline().addLast("encoder", new MessageSerializer());
							socketChannel.pipeline().addLast("packet_handler", new NettyChatHandler());
						}
					}).option(ChannelOption.SO_BACKLOG, Integer.MAX_VALUE).childOption(ChannelOption.SO_KEEPALIVE, true)
					.childOption(ChannelOption.TCP_NODELAY, true);
			
			ChannelFuture channelFuture = serverBootstrap.bind(this.port).sync();
			this.channel = channelFuture.channel();
			channelFuture.channel().closeFuture().sync();
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}
}