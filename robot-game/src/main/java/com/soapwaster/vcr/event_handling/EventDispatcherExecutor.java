package com.soapwaster.vcr.event_handling;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class EventDispatcher implements Runnable {
	
	BlockingDeque<Event> eventQueue;
	Set<Listener> listeners;
	

	public EventDispatcher(BlockingDeque<Event> eventQueue, Set<Listener> listeners) {
		this.eventQueue = eventQueue;
		this.listeners = listeners;	
	}

	@Override
	public void run() {
		while(true) {
			Event eventToDispatch;
			try {
				eventToDispatch = eventQueue.take();
				System.out.println(eventToDispatch.getSource());
				if(eventToDispatch.getClass() == StopEvent.class) {
					return;
				}
				System.out.println(listeners.size());
				if(!listeners.contains(eventToDispatch.getSource())) {
					continue;
				}
				if(eventToDispatch.getDestination() == null) {
					for (Listener receiver : listeners) {
						if(!receiver.equals(eventToDispatch.getSource())) {
							receiver.execute(eventToDispatch);
						}
					}
				}
				else {
					eventToDispatch.getDestination().execute(eventToDispatch);
				}
			} catch (InterruptedException e) {
				System.out.println("Fine");
				break;
			}
			
		}
	}

}
