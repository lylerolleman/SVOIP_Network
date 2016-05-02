package exec.commands;

import display.DisplayManager;
import network.NetworkManager;
import network.SVOIPConnection;
import exec.messages.SVOIPMessage;

public class Connect implements SVOIPCommand {
	private SVOIPConnection con;
	private String id;
	
	public Connect(String id, SVOIPConnection con) {
		this.id = id;
		this.con = con;
	}
	
	@Override
	public void execute(SVOIPMessage message) {
		NetworkManager.establishConnection(id, con);
		con.sendMessage("REPLY " + NetworkManager.getID() + ";\r\n");
		//DisplayManager.display("Connected to " + id);
		DisplayManager.addUser(id);
	}

}
