package exec.messages;

import exec.commands.SVOIPCommand;

public class SimpleMessage implements SVOIPMessage {
	private SVOIPCommand command;
	
	public SimpleMessage(SVOIPCommand command) {
		this.command = command;
	}

	@Override
	public void execute() {
		command.execute(this);
	}

	@Override
	public String[] getArgs() {
		return null;
	}

}
