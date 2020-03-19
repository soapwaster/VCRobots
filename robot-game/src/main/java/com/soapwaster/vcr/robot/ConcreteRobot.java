package com.soapwaster.vcr.robot;

import com.soapwaster.vcr.behaviour.BehaviourEnum;
import com.soapwaster.vcr.robot_game.Position2D;

public class ConcreteRobot extends Robot {
	
	public ConcreteRobot(String name) {
		super(name);
	}

	public ConcreteRobot(String name, BehaviourEnum behaviourType, Position2D position) {
		super(name, behaviourType);
		this.position = position;
	}

	

}
