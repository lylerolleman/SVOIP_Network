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
		NetworkManager.establishConnection("test", "192.168.0.20", 25123);
		NetworkManager.sendMessage("test", "CONNECT test;\r\n");
		/*try {
			SVOIPConnection con = new SVOIPConnection("192.168.0.20", 25123);
			con.sendMessage("CONNECT test;\r\n");
			//con.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		/*try {
			Socket sock = new Socket("192.168.0.20", 25123);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream( )));
			writer.write("CONNECT test;");
			writer.flush();
			writer.close();
			sock.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

}
