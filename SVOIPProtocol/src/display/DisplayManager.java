package display;

import java.util.LinkedList;

public class DisplayManager {
	private static LinkedList<DisplayListener> listeners = new LinkedList<DisplayListener>();
	
	public static void addDisplayListener(DisplayListener listener) {
		listeners.add(listener);
	}
	
	public static void clearDisplayListeners() {
		listeners.clear();
	}
	
	public static void display(String message) {
		for (DisplayListener listener : listeners) {
			listener.display(message);
		}
	}
	
	public static void addUser(String user) {
		for (DisplayListener listener : listeners) {
			listener.addUser(user);
		}
	}
	
	public static void removeUser(String user) {
		for (DisplayListener listener : listeners) {
			listener.removeUser(user);
		}
	}
}
