package exec.threads;

import network.SVOIPAudioConnection;

public class SpeakerThread extends Thread {
	private SVOIPAudioConnection acon;
	
	public SpeakerThread(SVOIPAudioConnection acon) {
		this.acon = acon;
	}
	
	public void run() {
		acon.readAudio();;
	}
}
