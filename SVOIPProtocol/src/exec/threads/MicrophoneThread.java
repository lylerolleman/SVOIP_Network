package exec.threads;

import network.SVOIPAudioConnection;

public class MicrophoneThread extends Thread {
	private SVOIPAudioConnection acon;
	
	public MicrophoneThread(SVOIPAudioConnection acon) {
		this.acon = acon;
	}
	
	public void run() {
		acon.transmitMicrophoneData();
	}
}
