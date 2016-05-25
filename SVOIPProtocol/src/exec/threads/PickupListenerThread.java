package exec.threads;

import network.SVOIPAudioConnection;

public class PickupListenerThread extends Thread {
	private SVOIPAudioConnection acon;
	
	public PickupListenerThread(SVOIPAudioConnection acon) {
		this.acon = acon;
	}
	
	public void run() {
		acon.awaitPickup();
		MicrophoneThread mic = new MicrophoneThread(acon);
		SpeakerThread speakers = new SpeakerThread(acon);
		mic.start();
		speakers.start();
	}

}
