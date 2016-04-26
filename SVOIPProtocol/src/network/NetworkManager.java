package network;

import java.io.IOException;
import java.util.HashMap;

import exec.ExecutionManager;
import exec.threads.ServerListenerThread;

public class NetworkManager {
	private static HashMap<String, SVOIPConnection> connections;
	private static int listenport = 25123;
	private static String uid = "";
	
	public static void setID(String id) {uid = id;}
	public static String getID() {return uid;}
	
	public static void initNetwork() {
		connections = new HashMap<String, SVOIPConnection>();
		ExecutionManager.startConsumer();
		new ServerListenerThread(listenport).start();
	}
	
	public static void establishConnection(String id, String address, int port) {
		try {
			new SVOIPConnection(address, port).sendMessage("CONNECT " + id + ";\r\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
	
	public static void sendRawMessage(String id, String message) {
		System.out.println("message to " + id);
		connections.get(id).sendMessage(message);
	}
	
	public static void sendMessage(String cid, String uid, String message) {
		sendRawMessage(cid, "MSG " + uid + " \"" + message + "\";\r\n");
	}
	
}
