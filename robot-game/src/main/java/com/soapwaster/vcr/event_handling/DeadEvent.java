package com.soapwaster.vcr.event_handling;

public class DeadEvent extends Event{

	public DeadEvent(Listener source, Listener destination) {
		super(source, destination,Priority.HIGH_PRIORITY);
	}

	@Override
	public EventTypeEnum getType() {
		return EventTypeEnum.Dead;
	}

}
