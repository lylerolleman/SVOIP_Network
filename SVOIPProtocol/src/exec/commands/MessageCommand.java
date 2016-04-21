package exec.commands;

import display.DisplayManager;
import exec.messages.SVOIPMessage;

public class MessageCommand implements SVOIPCommand {
	private String nick;
	
	public MessageCommand(String nick) {
		this.nick = nick;
	}
	@Override
	public void execute(SVOIPMessage message) {
		//System.out.println(nick + ": " + message.getArgs()[0]);
		DisplayManager.display(nick + ": " + message.getArgs()[0]);
	}

}
