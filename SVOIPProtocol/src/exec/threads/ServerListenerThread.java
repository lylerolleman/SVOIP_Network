package exec.threads;

import java.io.IOException;
import java.net.ServerSocket;

import network.NetworkManager;
import network.SVOIPConnection;

public class ServerListenerThread extends Thread {
	private int listenport;
	public ServerListenerThread(int port) {
		listenport = port;
	}
	public void run() {
		try {
			ServerSocket ss = new ServerSocket(listenport);
			while (true) {
				new SVOIPConnection(ss.accept());
				System.out.println("New client connected");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
