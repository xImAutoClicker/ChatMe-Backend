package de.fhdw.nohn.cm.backed.network.packet.in;

import de.fhdw.nohn.cm.backed.network.packet.PacketUtil;
import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PacketInLogin implements InPacket {
	
	private final int packetId = 0;
	
	private String username;
	
	@Override
	public void readPacket(ByteBuf inBuffer) throws Exception {
		this.username = PacketUtil.readString(inBuffer);
	}
}
