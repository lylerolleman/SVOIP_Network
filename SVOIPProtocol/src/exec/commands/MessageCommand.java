package exec.commands;

import exec.messages.SVOIPMessage;

public class MessageCommand implements SVOIPCommand {
	private String nick;
	
	public MessageCommand(String nick) {
		this.nick = nick;
	}
	@Override
	public void execute(SVOIPMessage message) {
		System.out.println(nick + ": " + message.getArgs()[0]);
	}

}
