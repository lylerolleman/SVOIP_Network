package exec.commands;

import exec.messages.SVOIPMessage;

public interface SVOIPCommand {
	public void execute(SVOIPMessage message);
}
