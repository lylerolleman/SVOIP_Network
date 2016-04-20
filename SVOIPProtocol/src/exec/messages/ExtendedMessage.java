package exec.messages;

import exec.commands.SVOIPCommand;

public class ExtendedMessage implements SVOIPMessage {
	private SVOIPCommand command;
	String[] args;
	
	public ExtendedMessage(SVOIPCommand command, String arg) {
		this(command, new String[1]);
		args[0] = arg;
	}
	public ExtendedMessage(SVOIPCommand command, String[] args) {
		this.command = command;
		this.args = args;
	}

	@Override
	public void execute() {
		command.execute(this);
	}

	@Override
	public String[] getArgs() {
		return args;
	}
	

}
