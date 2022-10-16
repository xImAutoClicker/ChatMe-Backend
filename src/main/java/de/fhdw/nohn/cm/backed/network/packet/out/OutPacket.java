package de.fhdw.nohn.cm.backed.network.packet.out;

import io.netty.buffer.ByteBuf;

public interface OutPacket {
	
	int getPacketId();
	
	void writePacket(final ByteBuf buffer) throws Exception;
}
