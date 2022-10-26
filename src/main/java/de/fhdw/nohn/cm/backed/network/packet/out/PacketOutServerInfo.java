package de.fhdw.nohn.cm.backed.network.packet.out;

import de.fhdw.nohn.cm.backed.network.packet.PacketUtil;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PacketOutServerInfo implements OutPacket {
	
	private final int packetId = 1;
	
	private final String serverName, ip;
	
	private final int port;
	
	@Override
	public void writePacket(ByteBuf buffer) throws Exception {
		PacketUtil.writeString(buffer, this.serverName);
		PacketUtil.writeString(buffer, this.ip);
		buffer.writeInt(this.port);
	}
}
