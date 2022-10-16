package de.fhdw.nohn.cm.backed.network.netty;

import java.util.List;

import de.fhdw.nohn.cm.backed.network.packet.PacketUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;

public class MessageLengthDeserializer extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf inBuffer, List<Object> out) throws Exception {
		inBuffer.markReaderIndex();
		
		byte[] lengthBytes = new byte[3];

		for (int i = 0; i < 3; i++) {
			if (!inBuffer.isReadable()) {
				inBuffer.resetReaderIndex();
				return;
			}
			lengthBytes[i] = inBuffer.readByte();
			
			// If the byte is higher/equal null. -> Length is available
			if (lengthBytes[i] >= 0) {
				try {
					int packetLength = PacketUtil.readVarInt(inBuffer);
					if (inBuffer.readableBytes() < packetLength) {
						inBuffer.resetReaderIndex();
						return;
					}
					out.add(inBuffer.readBytes(packetLength));
				} finally {
					inBuffer.release();
				}
				return;
			}
		}
		throw new CorruptedFrameException("length wider than 21-bit");
	}

}

