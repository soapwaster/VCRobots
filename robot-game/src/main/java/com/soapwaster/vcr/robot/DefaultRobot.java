package com.soapwaster.vcr.robot;

import com.soapwaster.vcr.behaviour.BehaviourEnum;
import com.soapwaster.vcr.robot_game.Position2D;

public class DefaultRobot extends Robot {
	
	public DefaultRobot(String name) {
		super(name, BehaviourEnum.Default);
	}

	public DefaultRobot(String name, Position2D position) {
		super(name, BehaviourEnum.Default);
		this.position = position;
	}

	

}
