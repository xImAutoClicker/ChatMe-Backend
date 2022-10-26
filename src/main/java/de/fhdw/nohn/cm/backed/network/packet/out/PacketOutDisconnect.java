package de.fhdw.nohn.cm.backed.network.packet.out;

import de.fhdw.nohn.cm.backed.network.packet.PacketUtil;
import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class PacketOutDisconnect implements OutPacket {
	
	@Getter
	private final int packetId = 2;
	
	private String reason = "noReason";
	
	@Override
	public void writePacket(ByteBuf buffer) throws Exception {
		PacketUtil.writeString(buffer, this.reason);
	}
}
