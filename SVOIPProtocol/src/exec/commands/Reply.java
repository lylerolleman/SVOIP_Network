package exec.commands;

import network.NetworkManager;
import network.SVOIPConnection;
import display.DisplayManager;
import exec.messages.SVOIPMessage;

public class Reply implements SVOIPCommand {
	private SVOIPConnection con;
	private String id;
	
	public Reply(String id, SVOIPConnection con) {
		this.id = id;
		this.con = con;
	}
	
	@Override
	public void execute(SVOIPMessage message) {
		NetworkManager.establishConnection(id, con);
		DisplayManager.display("Reply: Connected to " + id);
		DisplayManager.displaySecondary(id);
	}

}
