package de.fhdw.nohn.cm.backed.network.server;

import de.fhdw.nohn.cm.backed.ChatMeBackend;
import de.fhdw.nohn.cm.backed.network.packet.in.InPacket;
import de.fhdw.nohn.cm.backed.network.packet.in.PacketInLogin;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;

public class NettyLoginHandler extends SimpleChannelInboundHandler<Object> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
		new Thread(() -> {
			System.out.println("Packet received! ");
			
			try {
				if(!(msg instanceof InPacket)) return;
				
				InPacket packet = (InPacket) msg;
				
				if(packet instanceof PacketInLogin) {
					ChatMeBackend.getInstance().getUserEntryHandler().handleUserLogin(ctx.channel(), (PacketInLogin) packet);
				}
			} finally {
				ReferenceCountUtil.release(msg);
			}
		}).start();
	}
	
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		System.out.println("fiwhofiuwhf");
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("Connected client: " + ctx.channel().toString());
	}
	
	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		System.out.println("R Connected client: " + ctx.channel().toString());
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Handle Exception
		cause.printStackTrace();
	}
}
