package exec.threads;

import java.util.LinkedList;

import exec.messages.SVOIPMessage;

public class ConsumerThread extends Thread {
	private LinkedList<SVOIPMessage> messages;
	private boolean closed = false;
	
	public ConsumerThread(LinkedList<SVOIPMessage> messages) {
		this.messages = messages;
	}
	
	public void run() {
		while (true) {
			synchronized (messages) {
				try {
					messages.wait();
					if (closed) {
						return;
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				while (messages.size() > 0) {
					messages.pop().execute();
				}
			}
		}
	}
	
	public void close() {
		closed = true;
	}
}
