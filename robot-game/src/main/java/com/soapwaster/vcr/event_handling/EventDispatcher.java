package com.soapwaster.vcr.event_handling;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.LinkedBlockingDeque;


public class GameEventDispatcher {
	
	public BlockingDeque<Event> eventQueue;
	public Set<Listener> listeners;
	private Thread dispatcherThread;
	
	public GameEventDispatcher() {
		eventQueue = new LinkedBlockingDeque<Event>();
		listeners = new HashSet<>();	
		listeners = new CopyOnWriteArraySet<Listener>();
	}
	
	public void registerListener(Listener listeners) {
		this.listeners.add(listeners);	
	}
	
	public void addListener(Listener lr) {
		if(lr == null) {
			return;
		}
		listeners.add(lr);
	}
	
	public void removeListener(Listener lr) {
		listeners.remove(lr);
		
	}

	public void addEvent(Event e) {
		if(e == null) {
			return;
		}
		if(e.getPriority().equals(Priority.HIGH_PRIORITY)) {
			eventQueue.offerFirst(e);
		}
		else {
			eventQueue.offerLast(e);
		}
		
	}
	
	public void startDispatcher() {
		dispatcherThread = new Thread(new EventDispatcher(eventQueue, listeners));
		dispatcherThread.start();
	}
	
	public void stopDispatcher() {
		this.addEvent(new StopEvent(null, null));
	}
	
}
