package com.soapwaster.vcr.event_handling;

import java.util.Set;
import java.util.concurrent.BlockingDeque;

public class EventDispatcherExecutor implements Runnable {
	
	BlockingDeque<Event> eventDeque;
	Set<Listener> listeners;
	

	public EventDispatcherExecutor(BlockingDeque<Event> eventDeque, Set<Listener> listeners) {
		this.eventDeque = eventDeque;
		this.listeners = listeners;	
	}

	@Override
	public void run() {
		while(true) {
			Event eventToDispatch;
			try {
				eventToDispatch = eventDeque.take();
				
				//if the received event is the stop, then stop delivering messages
				if(eventToDispatch.getClass() == StopEvent.class) {
					return;
				}
				
				if(!listeners.contains(eventToDispatch.getSource())) {
					continue;
				}
				
				//if the message is destined to everybody
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
				break;
			}
			
		}
	}

}
