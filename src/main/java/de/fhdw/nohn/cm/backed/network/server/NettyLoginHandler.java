package de.fhdw.nohn.cm.backed.network.server;

import de.fhdw.nohn.cm.backed.ChatMeBackend;
import de.fhdw.nohn.cm.backed.network.packet.in.InPacket;
import de.fhdw.nohn.cm.backed.network.packet.in.PacketInLogin;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class NettyLoginHandler extends SimpleChannelInboundHandler<Object> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
		if(!(msg instanceof InPacket)) return;
		
		InPacket packet = (InPacket) msg;
		
		if(packet instanceof PacketInLogin) {
			ChatMeBackend.getInstance().getUserEntryHandler().handleLogin((PacketInLogin) packet);
		}
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Handle Exception
	}
}
