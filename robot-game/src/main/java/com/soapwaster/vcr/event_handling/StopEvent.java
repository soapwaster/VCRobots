package com.soapwaster.vcr.event_handling;

public class StopEvent extends Event {

	public StopEvent(Listener source, Listener destination) {
		super(source, destination,Priority.HIGH_PRIORITY);
	}

	@Override
	public EventTypeEnum getType() {
		return EventTypeEnum.Stop;
	}
}
