package network;

import java.io.IOException;
import java.util.HashMap;

import exec.ExecutionManager;
import exec.threads.ServerListenerThread;

public class NetworkManager {
	private static HashMap<String, SVOIPConnection> connections;
	private static int listenport = 25123;
	
	public static void initNetwork() {
		connections = new HashMap<String, SVOIPConnection>();
		ExecutionManager.startConsumer();
		new ServerListenerThread(listenport).start();
	}
	
	public static void establishConnection(String id, String address, int port) {
		try {
			establishConnection(id, new SVOIPConnection(address, port));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void establishConnection(String id, SVOIPConnection con) {
		con.setID(id);
		connections.put(id, con);
	}
	
	public static void closeConnection(String id) {
		SVOIPConnection con = connections.get(id);
		connections.remove(id);
		con.close();
	}
	
	public static void sendMessage(String id, String message) {
		connections.get(id).sendMessage(message);
	}
}
