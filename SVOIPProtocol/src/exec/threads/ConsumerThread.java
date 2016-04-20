package exec.threads;

import java.util.LinkedList;

import exec.messages.SVOIPMessage;

public class ConsumerThread extends Thread {
	private LinkedList<SVOIPMessage> messages;
	
	public ConsumerThread(LinkedList<SVOIPMessage> messages) {
		this.messages = messages;
	}
	
	public void run() {
		while (true) {
			synchronized (messages) {
				try {
					messages.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				while (messages.size() > 0) {
					messages.pop().execute();
				}
			}
		}
	}
}
