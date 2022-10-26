package de.fhdw.nohn.cm.backed.user;

import java.util.HashMap;

import de.fhdw.nohn.cm.backed.ChatMeBackend;
import de.fhdw.nohn.cm.backed.network.packet.in.PacketInLogin;
import de.fhdw.nohn.cm.backed.network.packet.out.PacketOutDisconnect;
import de.fhdw.nohn.cm.backed.server.ServerEntry;
import io.netty.channel.Channel;

@SuppressWarnings("serial")
public class UserEntryHandler extends HashMap<String, UserEntry>{
	
	public void handleUserLogin(final Channel channel, final PacketInLogin packet) {
		if(super.containsKey(packet.getUsername())) {
			channel.writeAndFlush(new PacketOutDisconnect("This username is already in use by another user!"));
//			return;
		}
		System.out.println("User " + packet.getUsername() + " connected!");
		
		final ServerEntry serverEntry = ChatMeBackend.getInstance().getServerEntryHandler().getRandomServer();
		
		if(serverEntry == null) {
			channel.writeAndFlush(new PacketOutDisconnect("No Chat-Server found! :("));
			System.out.println("User " + packet.getUsername() + " disconnected! (No server found!");
			return;
		}
		System.out.println("Server: " + serverEntry.getServerName());
		
		final UserEntry user = new UserEntry(packet.getUsername());
		user.setChannel(channel); // Login Channel
		user.connectToServer(serverEntry);
		
		serverEntry.getUserEntries().add(user);
		
		super.put(user.getUsername(), user);
	}
}