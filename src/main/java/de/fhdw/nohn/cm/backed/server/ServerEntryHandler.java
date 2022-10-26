package de.fhdw.nohn.cm.backed.server;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import com.google.common.collect.Lists;

import de.fhdw.nohn.cm.backed.ChatMeBackend;
import de.fhdw.nohn.cm.backed.network.packet.in.PacketInMessage;
import de.fhdw.nohn.cm.backed.network.packet.out.PacketOutDisconnect;
import de.fhdw.nohn.cm.backed.network.packet.out.PacketOutMessage;
import de.fhdw.nohn.cm.backed.network.server.NettyChatServer;
import de.fhdw.nohn.cm.backed.user.UserEntry;
import io.netty.channel.Channel;

public class ServerEntryHandler {
	
	private int serverPoolSize = 1, minPort = 2000;
	
	private List<ServerEntry> serverList = Lists.newArrayList();
	
	public ServerEntryHandler() {
		new Thread(() -> {
			
			while(true) {
				if(this.serverPoolSize > this.serverList.size()) {
					System.out.println("Creating new Chat-Server...");
					
					synchronized (this.serverList) {
						final NettyChatServer nettyChatServer = new NettyChatServer(this.minPort++);
						nettyChatServer.start();
						
						int serverCount = this.serverList.size();
						final String serverName = "#" + (++serverCount);
						
						ServerEntry serverEntry = new ServerEntry(ServerEntryHandler.getHostIp(), serverName, nettyChatServer.getPort(), nettyChatServer.getChannel());
						this.serverList.add(serverEntry);
						
						System.out.println("Added a Chat-Server! [Name: " + serverName + " Port: " + nettyChatServer.getPort() + "]");
					}
				}
			}
		}).start();
	}
	
	public final ServerEntry getRandomServer() {
		if(this.serverList.isEmpty()) return null;
		
		return this.serverList.get(new Random().nextInt(this.serverList.size()));
	}
	
	public final ServerEntry getServerEntryByName(final String serverName) {
		Optional<ServerEntry> serverEntry = this.serverList.parallelStream().filter(server -> server.getServerName().equals(serverName)).findFirst();
		
		return serverEntry.isPresent() ? serverEntry.get() : null;
	}
	
	public final void handleIncMessage(final Channel channel, final PacketInMessage packet) {
		final UserEntry userEntry = ChatMeBackend.getInstance().getUserEntryHandler().get(packet.getUsername());
		
		// Not connected anymore???
		if(userEntry == null) return;
		
		// Change Login Channel to Chat Channel
		userEntry.setChannel(channel);
		
		System.out.println("[Message] " + packet.getUsername() + ": " + packet.getMessage());
		
		final ServerEntry serverEntry = this.getServerEntryByName(packet.getServerName());
		
		if(serverEntry == null) {
			System.out.println("Server is null! " + packet.getServerName());
			
			channel.writeAndFlush(new PacketOutDisconnect("Hoops! Your current server is apparently no longer active."));
			return;
		}
		serverEntry.getUserEntries().forEach(user -> {
			System.out.println("Sending Packet " + user.getUsername());
			
			user.sendPacket(new PacketOutMessage(packet.getUsername(), packet.getMessage(), packet.getMillis()));
			
			System.out.println("Done!");
		});
	}
	
	public static String getHostIp() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			return "127.0.0.1";
		}
	}
}
