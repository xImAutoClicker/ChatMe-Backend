package de.fhdw.nohn.cm.backed.network.netty;

import de.fhdw.nohn.cm.backed.network.packet.PacketUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

@SuppressWarnings("rawtypes")
public class MessageLengthSerializer extends MessageToByteEncoder {
	
	@Override
	protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
		if (!(msg instanceof ByteBuf)) return;
		
		ByteBuf in = (ByteBuf) msg;
		
		int readableBytes = in.readableBytes();
		int lengthByteSpace = PacketUtil.getVarIntLength(readableBytes);
		
		if (lengthByteSpace > 3) {
			throw new IllegalArgumentException("unable to fit " + lengthByteSpace + " into 3");
		}
		out.ensureWritable(lengthByteSpace + readableBytes);
		PacketUtil.writeVarInt(out,readableBytes);
		out.writeBytes(in, in.readerIndex(), readableBytes);
	}
}

