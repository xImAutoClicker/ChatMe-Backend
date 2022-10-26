package de.fhdw.nohn.cm.backed.network.server;

import de.fhdw.nohn.cm.backed.ChatMeBackend;
import de.fhdw.nohn.cm.backed.network.packet.in.InPacket;
import de.fhdw.nohn.cm.backed.network.packet.in.PacketInMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;

public class NettyChatHandler extends SimpleChannelInboundHandler<Object> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
		new Thread(() -> {
			if(!(msg instanceof InPacket)) return;
			
			try {
				InPacket packet = (InPacket) msg;
				
				if(packet instanceof PacketInMessage) {
					ChatMeBackend.getInstance().getServerEntryHandler().handleIncMessage(ctx.channel(),(PacketInMessage) packet);
				}
			} finally {
				ReferenceCountUtil.release(msg);
			}
		}).start();
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Handle Exception
	}
}
