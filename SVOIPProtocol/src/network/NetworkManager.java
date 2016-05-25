package network;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import exec.ExecutionManager;
import exec.threads.CallListenerThread;
import exec.threads.ServerListenerThread;

public class NetworkManager {
	private static HashMap<String, SVOIPConnection> connections;
	private static int listenport = 25123;
	private static String uid = "";
	private static ServerListenerThread lt;
	private static CallListenerThread clt;
	
	public static void setID(String id) {uid = id;}
	public static String getID() {return uid;}
	
	public static void initNetwork() {
		connections = new HashMap<String, SVOIPConnection>();
		ExecutionManager.startConsumer();
		lt = new ServerListenerThread(listenport);
		clt = new CallListenerThread(listenport);
		lt.start();
		clt.start();
	}
	
	public static void closeNetwork() {
		lt.close();
		clt.close();
		closeAllConnections();
		ExecutionManager.stopConsumer();
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
	
	public static void closeAllConnections() {
		Set<String> set = connections.keySet();
		for (String id : set) {
			closeConnection(id);
		}
	}
	
	public static void callUser(String id) {
		sendRawMessage(id, "CALL " + clt.getPort() + ";\r\n");
	}
	
	public static void setAudioConnection(String id, SVOIPAudioConnection acon) {
		connections.get(id).setAudioConnection(acon);
	}
	
	public static void sendRawMessage(String id, String message) {
		//System.out.println("message to " + id);
		connections.get(id).sendMessage(message);
	}
	
	public static void sendMessage(String cid, String uid, String message) {
		sendRawMessage(cid, "MSG " + uid + " \"" + message + "\";\r\n");
	}
	
	public static void sendDisconnect(String id) {
		sendRawMessage(id, "DISCONNECT;\r\n");
		closeConnection(id);
	}
	
}
