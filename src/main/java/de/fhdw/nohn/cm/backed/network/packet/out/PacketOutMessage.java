package de.fhdw.nohn.cm.backed.network.packet.out;

import de.fhdw.nohn.cm.backed.network.packet.PacketUtil;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PacketOutMessage implements OutPacket {

	private final int packetId = 0;
	
	private final String username, message;
	
	private final long dateInMillis;
	
	@Override
	public void writePacket(ByteBuf buffer) throws Exception {
		PacketUtil.writeString(buffer, this.username);
		PacketUtil.writeString(buffer, this.message);
		buffer.writeLong(this.dateInMillis);
	}
}
