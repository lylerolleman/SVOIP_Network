package exec;

import java.util.LinkedList;

import exec.messages.SVOIPMessage;
import exec.threads.ConsumerThread;

public class ExecutionManager {
	private static final LinkedList<SVOIPMessage> messages = new LinkedList<SVOIPMessage>();
	private static ConsumerThread ct;
	
	public static void enqueueMessages(LinkedList<SVOIPMessage> nmes) {
		synchronized (messages) {
			messages.addAll(nmes);
			messages.notify();
		}
	}
	
	public static void startConsumer() {
		ct = new ConsumerThread(messages);
		ct.start();
	}
	
	public static void stopConsumer() {
		synchronized (messages) {
			messages.clear();
			ct.close();
			messages.notify();
		}
		
	}
}
