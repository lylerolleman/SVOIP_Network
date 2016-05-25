package network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

public class SVOIPAudioConnection {
	private Socket socket;
	private AudioFormat format;
	private boolean open;

	public SVOIPAudioConnection(String address, int port) {
		try {
			this.socket = new Socket(address, port);
			this.open = false;
			this.format = new AudioFormat(8000.0f, 16, 1, true, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public SVOIPAudioConnection(Socket socket) {
		this.socket = socket;
		this.open = false;
		this.format = new AudioFormat(8000.0f, 16, 1, true, true);
	}
	
	public void awaitPickup() {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream( )));
			String line = reader.readLine();
			if (line.startsWith("PICKUP")) {
				String[] temp = line.substring(0, line.indexOf(";")).split(" ");
				try {
					NetworkManager.setAudioConnection(temp[1], this);
				} catch (NullPointerException npe) {
					System.err.println("Connection id doesn't exist");
					npe.printStackTrace();
				}
			}
			//reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendPickup() {
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream( )));
			writer.write("PICKUP " + NetworkManager.getID() + ";\r\n");
			writer.flush();
			//writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void transmitMicrophoneData() {
	    TargetDataLine microphone;

	    try {
	        microphone = AudioSystem.getTargetDataLine(format);

	        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
	        microphone = (TargetDataLine) AudioSystem.getLine(info);
	        microphone.open(format);

	        OutputStream out = socket.getOutputStream();
	        int numBytesRead;
	        int CHUNK_SIZE = 1024;
	        byte[] data = new byte[microphone.getBufferSize() / 5];
	        microphone.start();

	        open = true;
	        int bytesRead = 0;
	        
	        while (open) {
	            numBytesRead = microphone.read(data, 0, CHUNK_SIZE);
	            //bytesRead += numBytesRead;
	            // write the mic data to a stream for use later
	            out.write(data, 0, numBytesRead); 
	            out.flush();
	            // write mic data to stream for immediate playback
	            //speakers.write(data, 0, numBytesRead);
	        }
	        
	        microphone.close();
	    } catch (LineUnavailableException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public void readAudio() {
	    SourceDataLine speakers;
		DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, format);
        try {
        	InputStream input = socket.getInputStream();
			speakers = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
			speakers.open(format);
			speakers.start();
			byte[] data = new byte[speakers.getBufferSize()/5];
			int bytesread = 0;
			while ((bytesread = input.read(data)) > 0) {
				speakers.write(data, 0, bytesread);
			}
			
			speakers.drain();
	        speakers.close();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SocketException se) {
			System.err.println(se.getMessage());
			close();
		}
        catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}
	
	public void close() {
		open = false;
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
