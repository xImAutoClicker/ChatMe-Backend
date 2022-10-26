package de.fhdw.nohn.cm.backed;

public class Main {

	public static void main(String[] args) {
		System.out.println("Preparing ChatMe-Backend by Jannik Nohn to start...\n\n");
		
		new Thread(() -> {
			new ChatMeBackend().startSystem();
		}).start();
	}
}
