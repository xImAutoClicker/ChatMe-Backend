package de.fhdw.nohn.cm.backed.user;

import de.fhdw.nohn.cm.backed.network.packet.out.OutPacket;
import de.fhdw.nohn.cm.backed.network.packet.out.PacketOutServerInfo;
import de.fhdw.nohn.cm.backed.server.ServerEntry;
import io.netty.channel.Channel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
public class UserEntry {
	
	@Setter
	private Channel channel;
	
	private final String username;
	
	private ServerEntry connectedServer;
	
	
	
	public final void connectToServer(final ServerEntry server) {
		this.connectedServer = server;
		
		this.sendPacket(new PacketOutServerInfo(server.getServerName(), server.getHost(), server.getPort()));
	}
	
	public final void sendPacket(final OutPacket packet) {
		this.channel.writeAndFlush(packet);
	}
}
