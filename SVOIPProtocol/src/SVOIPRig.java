import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

import network.NetworkManager;
import network.SVOIPConnection;


public class SVOIPRig {
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		NetworkManager.initNetwork();
		if (args.length > 0 && args[0].equals("test")) {
			NetworkManager.establishConnection("test", "127.0.0.1", 25123);
			NetworkManager.sendRawMessage("test", "CONNECT test;\r\n");
			Scanner sc = new Scanner(System.in);
			while (true) {
				String mes = sc.nextLine();
				NetworkManager.sendRawMessage("test", "MSG test \"" + mes + "\";\r\n");
			}
		}
		
	}

}
