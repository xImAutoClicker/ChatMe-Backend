package de.fhdw.nohn.cm.backed.server;

import java.util.List;

import com.google.common.collect.Lists;

import de.fhdw.nohn.cm.backed.network.packet.out.OutPacket;
import de.fhdw.nohn.cm.backed.user.UserEntry;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ServerEntry {
	
	private final String serverName, ipAddresse;
	
	private final int port;
	
	private final Channel channel;
	
	private final List<UserEntry> userEntries = Lists.newArrayList();
	
	
	
	public void sendPacket(final OutPacket packet, ChannelFutureListener...channelFutureListeners) {
		this.channel.writeAndFlush(packet);
	}
}
