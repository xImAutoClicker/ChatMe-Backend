package de.fhdw.nohn.cm.backed.user;

import de.fhdw.nohn.cm.backed.server.ServerEntry;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UserEntry {
	
	private final String username, password;
	
	private ServerEntry connectedServer;
	
	public final void connectToServer(final ServerEntry server) {
		this.connectedServer = server;
	}
}
