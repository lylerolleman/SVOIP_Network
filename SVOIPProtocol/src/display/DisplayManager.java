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
}
