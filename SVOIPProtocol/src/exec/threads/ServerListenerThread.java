package exec.threads;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;

import display.DisplayManager;
import network.NetworkManager;
import network.SVOIPConnection;

public class ServerListenerThread extends Thread {
	private int listenport;
	public ServerListenerThread(int port) {
		listenport = port;
	}
	public void run() {
		try {
			ServerSocket ss;
			while (true) {
				try {
					ss = new ServerSocket(listenport);
				} catch (BindException be) {
					listenport++;
					continue;
				}
				DisplayManager.display("Listening on port: " + listenport);
				break;
			}
			while (true) {
				SVOIPConnection con = new SVOIPConnection(ss.accept());
				System.out.println("New client connected");
				con.sendMessage("REPLY " + NetworkManager.getID() + ";\r\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}
