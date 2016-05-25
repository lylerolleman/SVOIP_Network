package exec.threads;

import java.io.IOException;

import network.SVOIPAudioConnection;

public class CallListenerThread extends ServerListenerThread {

	public CallListenerThread(int port) {
		super(port);
	}
	
	
	public void run() {
		try {
			super.establishListener();
			while (true) {
				SVOIPAudioConnection acon = new SVOIPAudioConnection(super.ss.accept());
				new PickupListenerThread(acon).start();
			}
		} catch (IOException e) {
			if (super.ss.isClosed()) {
				System.err.println("server closing...");
				return;
			}
			e.printStackTrace();
		}
	}

}
