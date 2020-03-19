package com.soapwaster.vcr.event_handling;

import com.soapwaster.vcr.robot_game.Position2D;

public class MoveToEvent extends Event{
	
	Position2D movePosition;

	public MoveToEvent(Listener source, Listener destination, Position2D movePosition) {
		super(source, destination,Priority.LOW_PRIORITY);
		this.movePosition = movePosition;
	}
	
	public Position2D getPosition() {
		return movePosition;
	}
	
	@Override
	public EventTypeEnum getType() {
		return EventTypeEnum.MoveTo;
	}

}
