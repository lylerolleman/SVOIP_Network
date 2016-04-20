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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
