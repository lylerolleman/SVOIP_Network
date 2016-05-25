package exec.commands;

import network.SVOIPAudioConnection;
import network.SVOIPConnection;
import exec.messages.SVOIPMessage;
import exec.threads.MicrophoneThread;
import exec.threads.SpeakerThread;

public class Call implements SVOIPCommand {
	private SVOIPConnection con;
	private int port;
	
	public Call(SVOIPConnection con, int port) {
		this.con = con;
		this.port = port;
	}
	
	@Override
	public void execute(SVOIPMessage message) {
		SVOIPAudioConnection acon = new SVOIPAudioConnection(con.getAddress(), port);
		acon.sendPickup();
		this.con.setAudioConnection(acon);
		MicrophoneThread mic = new MicrophoneThread(acon);
		SpeakerThread speakers = new SpeakerThread(acon);
		mic.start();
		speakers.start();
	}

}
