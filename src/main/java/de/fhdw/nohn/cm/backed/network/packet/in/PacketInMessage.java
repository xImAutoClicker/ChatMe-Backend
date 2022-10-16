package de.fhdw.nohn.cm.backed.network.packet.in;

import de.fhdw.nohn.cm.backed.network.packet.*;
import io.netty.buffer.ByteBuf;
import lombok.Getter;

@Getter
public class PacketInMessage implements InPacket {

	private final int packetId = 1;
	
	private String username, message;
	
	private long millis;

	@Override
	public void readPacket(ByteBuf inBuffer) throws Exception {
		this.username = PacketUtil.readString(inBuffer);
		this.message = PacketUtil.readString(inBuffer);
		this.millis = inBuffer.readLong();
	}
}
