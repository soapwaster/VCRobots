package com.soapwaster.vcr.event_handling;

public abstract class Event {
	private Listener source;
	private Listener destination;
	private Priority priority;

	public Event(Listener source, Listener destination) {
		priority = Priority.LOW_PRIORITY;
		this.source = source;
		this.destination = destination;
	}	
	
	public Event(Listener source, Listener destination, Priority priority) {
		this(source,destination);
		this.priority = priority;
	}	

	public Listener getSource() {
		return source;
	}

	public Listener getDestination() {
		return destination;
	}
	
	public Priority getPriority() {
		return priority;
	}

	public boolean equals(Object o) {
		if (o != null && getClass().equals(o.getClass())) {
			Event e = (Event) o;
			return source == e.source && destination == e.destination;
		} else
			return false;
	}

}