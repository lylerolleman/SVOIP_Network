package exec.threads;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;

import display.DisplayManager;
import network.NetworkManager;
import network.SVOIPConnection;

public class ServerListenerThread extends Thread {
	protected int listenport;
	protected ServerSocket ss;

	public ServerListenerThread(int port) {
		listenport = port;
	}
	public void run() {
		try {
			establishListener();
			DisplayManager.display("Listening on port: " + listenport);
			while (true) {
				SVOIPConnection con = new SVOIPConnection(ss.accept());
				System.out.println("New client connected");
				con.sendMessage("REPLY " + NetworkManager.getID() + ";\r\n");
			}
		} catch (IOException e) {
			if (ss.isClosed()) {
				System.err.println("server closing...");
				return;
			}
			e.printStackTrace();
		} 
	}

	protected void establishListener() throws IOException {
		while (true) {
			try {
				ss = new ServerSocket(listenport);
			} catch (BindException be) {
				listenport++;
				continue;
			}
			break;
		}
	}
	
	public int getPort() {
		return listenport;
	}

	public void close() {
		try {
			ss.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
