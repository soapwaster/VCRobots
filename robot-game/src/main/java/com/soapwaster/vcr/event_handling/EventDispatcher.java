package com.soapwaster.vcr.event_handling;

import java.util.Set;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.LinkedBlockingDeque;


public class EventDispatcher {
	
	public BlockingDeque<Event> eventQueue;
	public Set<Listener> listeners;
	private Thread dispatcherThread;
	
	public EventDispatcher() {
		eventQueue = new LinkedBlockingDeque<Event>();
		listeners = new CopyOnWriteArraySet<Listener>();
	}
	
	public void registerListener(Listener listener) {
		if(listener == null) {
			return;
		}
		listeners.add(listener);
	}
	
	public void unregisterListener(Listener listener) {
		listeners.remove(listener);
		
	}
	
	/**
	 * Adds an event to the event queue. If it has HIGH_PRIORITY, it will be put 
	 * on top of the queue to be executed first
	 * @param e an event
	 */
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
	
	/**
	 * Starts the dispatcher
	 */
	public void startDispatcher() {
		dispatcherThread = new Thread(new EventDispatcherExecutor(eventQueue, listeners));
		dispatcherThread.start();
	}
	
	/**
	 * Stops the dispatcher
	 */
	public void stopDispatcher() {
		this.addEvent(new StopEvent(null, null));
	}
	
}
