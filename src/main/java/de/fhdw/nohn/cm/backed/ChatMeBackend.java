package de.fhdw.nohn.cm.backed;

import de.fhdw.nohn.cm.backed.network.server.NettyChatServer;
import de.fhdw.nohn.cm.backed.network.server.NettyLoginServer;
import de.fhdw.nohn.cm.backed.server.ServerEntryHandler;
import de.fhdw.nohn.cm.backed.user.UserEntryHandler;
import lombok.Getter;

@Getter
public class ChatMeBackend {
	
	@Getter
	private static ChatMeBackend instance;
	
	private UserEntryHandler userEntryHandler;
	
	private ServerEntryHandler serverEntryHandler;
	
	private NettyLoginServer nettyLoginServer;
	
	private NettyChatServer nettyChatServer;
	
	public void startSystem() {
		instance = this;
		
		this.printLogo();
		
		System.out.println("Init User Handler...");
		this.userEntryHandler = new UserEntryHandler();
		
		System.out.println("Starting Login Server...");
		
		new Thread(() -> {
			this.nettyLoginServer = new NettyLoginServer(1999);
			this.nettyLoginServer.start();
		}).start();
		
		System.out.println("Init Server Handler...");
		this.serverEntryHandler = new ServerEntryHandler();
	}
	
	private void printLogo() {
		System.out.println("   _____ _           _   __  __ ______ \r\n"
				+ "  / ____| |         | | |  \\/  |  ____|\r\n"
				+ " | |    | |__   __ _| |_| \\  / | |__   \r\n"
				+ " | |    | '_ \\ / _` | __| |\\/| |  __|  \r\n"
				+ " | |____| | | | (_| | |_| |  | | |____ \r\n"
				+ "  \\_____|_| |_|\\__,_|\\__|_|  |_|______|\r\n"
				+ "                                       \r\n"
				+ "                                       ");
	}
}
