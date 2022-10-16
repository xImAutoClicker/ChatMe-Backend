package de.fhdw.nohn.cm.backed.network.netty;

import de.fhdw.nohn.cm.backed.network.packet.PacketUtil;
import de.fhdw.nohn.cm.backed.network.packet.out.OutPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

@SuppressWarnings("rawtypes")
public class MessageSerializer extends MessageToByteEncoder {

	@Override
	protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
		if (!(msg instanceof OutPacket)) return;
		
		final OutPacket outPacket = (OutPacket) msg;
		
		PacketUtil.writeVarInt(out, outPacket.getPacketId());
		outPacket.writePacket(out);
	}
}
