package de.fhdw.nohn.cm.backed.server;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import de.fhdw.nohn.cm.backed.network.server.NettyChatServer;

public class ServerEntryHandler {
	
	private int serverPoolSize = 1, minPort = 2000;
	
	private List<ServerEntry> serverList = Lists.newArrayList();
	
	public ServerEntryHandler() {
		new Thread(() -> {
			
			while(true) {
				if(this.serverPoolSize > this.serverList.size()) {
					System.out.println("Starting new Chat Server...");
					
					synchronized (this.serverList) {
						final NettyChatServer nettyChatServer = new NettyChatServer(this.minPort++);
						nettyChatServer.start();
						
						int serverCount = this.serverList.size();
						final String serverName = "#" + (++serverCount);
						
						ServerEntry serverEntry = new ServerEntry(serverName, "localhost", nettyChatServer.getPort(), nettyChatServer.getChannel());
						this.serverList.add(serverEntry);
						
						System.out.println("Added a new Chat Server! [Name: " + serverName + " Port: " + nettyChatServer.getPort());
					}
				}
			}
		}).start();
	}
	
	public final ServerEntry getRandomServer() {
		if(this.serverList.isEmpty()) return null;
		
		return this.serverList.get(new Random().nextInt(this.serverList.size()));
	}
}
