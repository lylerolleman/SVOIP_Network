import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

import network.NetworkManager;
import network.SVOIPConnection;


public class SVOIPRig {
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		NetworkManager.initNetwork();
		if (args.length > 0 && args[0].equals("test")) {
			NetworkManager.establishConnection("test", "192.168.0.20", 25123);
			NetworkManager.sendMessage("test", "CONNECT test;\r\n");
		}
		
	}

}
