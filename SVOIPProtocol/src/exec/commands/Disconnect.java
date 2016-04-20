package exec.commands;

import network.NetworkManager;
import network.SVOIPConnection;
import exec.messages.SVOIPMessage;

public class Disconnect implements SVOIPCommand {
	private String id;
	
	public Disconnect(String id) {
		this.id = id;
	}
	
	@Override
	public void execute(SVOIPMessage message) {
		NetworkManager.closeConnection(id);
	}

	
}