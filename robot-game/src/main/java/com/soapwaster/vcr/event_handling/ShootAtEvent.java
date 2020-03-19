package com.soapwaster.vcr.event_handling;

import com.soapwaster.vcr.robot_game.Position2D;

public class ShootAtEvent extends Event{
	
	private Position2D shootPosition;
	private double damage;

	public ShootAtEvent(Listener source, Listener destination, Position2D shootPosition, double damage) {
		super(source, destination);
		this.shootPosition = shootPosition;
		this.damage = damage;
		
	}
	
	public Position2D getPosition() {
		return shootPosition;
	}
	
	public double getDamage() {
		return damage;
	}
	
	@Override
	public EventTypeEnum getType() {
		return EventTypeEnum.ShootAt;
	}
	
	
}
