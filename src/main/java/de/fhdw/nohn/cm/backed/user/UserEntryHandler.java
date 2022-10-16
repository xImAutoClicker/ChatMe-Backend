package de.fhdw.nohn.cm.backed.user;

import java.util.HashMap;

import de.fhdw.nohn.cm.backed.ChatMeBackend;
import de.fhdw.nohn.cm.backed.network.packet.in.PacketInLogin;
import de.fhdw.nohn.cm.backed.server.ServerEntry;

@SuppressWarnings("serial")
public class UserEntryHandler extends HashMap<String, UserEntry>{
	
	
	public void handleLogin(final PacketInLogin packet) {
		final ServerEntry serverEntry = ChatMeBackend.getInstance().getServerEntryHandler().getRandomServer();
		
		if(serverEntry == null) {
			// Handle Disconnect!
			return;
		}
		final UserEntry user = new UserEntry(packet.getUsername(), packet.getPassword());
		user.connectToServer(serverEntry);
		
		super.put(user.getUsername(), user);
	}
}