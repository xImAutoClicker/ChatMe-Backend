package de.fhdw.nohn.cm.backed;

import de.fhdw.nohn.cm.backed.network.server.NettyLoginServer;
import de.fhdw.nohn.cm.backed.server.ServerEntryHandler;
import de.fhdw.nohn.cm.backed.user.UserEntryHandler;
import lombok.Getter;

@Getter
public class ChatMeBackend {
	
	@Getter
	private static ChatMeBackend instance;
	
	private UserEntryHandler userEntryHandler;
	
	private NettyLoginServer nettyLoginServer;
	
	private ServerEntryHandler serverEntryHandler;
	
	public void startSystem() {
		instance = this;
		
		this.userEntryHandler = new UserEntryHandler();
		this.serverEntryHandler = new ServerEntryHandler();
	}
}
