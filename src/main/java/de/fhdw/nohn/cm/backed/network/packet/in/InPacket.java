package de.fhdw.nohn.cm.backed.network.packet.in;

import io.netty.buffer.ByteBuf;

public interface InPacket {
	
	int getPacketId();
	
	void readPacket(final ByteBuf inBuffer) throws Exception;
}
