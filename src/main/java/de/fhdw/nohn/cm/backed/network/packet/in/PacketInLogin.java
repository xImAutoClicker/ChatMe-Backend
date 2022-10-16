package de.fhdw.nohn.cm.backed.network.packet.in;

import de.fhdw.nohn.cm.backed.network.packet.PacketUtil;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PacketInLogin implements InPacket {
	
	private final int packetId = 0;
	
	private String username, password;
	
	@Override
	public void readPacket(ByteBuf inBuffer) throws Exception {
		this.username = PacketUtil.readString(inBuffer);
		this.password = PacketUtil.readString(inBuffer);
	}
}
