package exec.threads;

import java.io.IOException;

import network.SVOIPConnection;

public class ReaderThread extends Thread {
	SVOIPConnection connection;
	
	public ReaderThread(SVOIPConnection connection) {
		this.connection = connection;
	}
	
	public void run() {
		try {
			connection.readAndParse();
		} catch (NullPointerException | IOException e) {
			System.err.println("closing reader...");
			return;
		}
	}
}
