package network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.LinkedList;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

import exec.ExecutionManager;
import exec.messages.SVOIPMessage;
import exec.threads.ReaderThread;
import protocolparser.ProtocolLexer;
import protocolparser.ProtocolParser;

public class SVOIPConnection {
	private String id;
	private Socket socket;
	private BufferedReader reader;
	private BufferedWriter writer;
	private ReaderThread rthread;
	
	public SVOIPConnection(String address, int port) throws UnknownHostException, IOException {
	    this(new Socket(address, port));
	}
	public SVOIPConnection(Socket socket) {
		try {
			this.socket = socket;
	    	writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream( )));
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream( )));
			rthread = new ReaderThread(this);
			rthread.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void readAndParse() throws IOException {
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				//System.out.println(line);
				//TODO change reader to get more from the stream at once
				ANTLRStringStream input = new ANTLRStringStream(line);
				ProtocolLexer lexer = new ProtocolLexer(input);
				CommonTokenStream tokenstream = new CommonTokenStream(lexer);
				try {
					LinkedList<SVOIPMessage> messages = new ProtocolParser(tokenstream, this).messages();
					ExecutionManager.enqueueMessages(messages);
				} catch (RecognitionException e) {
					e.printStackTrace();
				}
			}
		} catch (SocketException se) {
			System.err.println(se.getMessage());
			if (id != null) {
				NetworkManager.closeConnection(id);
			}
		}
	}
	
	public void sendMessage(String message) {
		try {
			writer.write(message);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setID(String id) {this.id = id;}
	public String getID() {
		if (id == null) {
			System.err.println("null ID error");
		}
		return id;
	}
	
	public void close() {
		try {
			
			writer.close();
			reader.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
