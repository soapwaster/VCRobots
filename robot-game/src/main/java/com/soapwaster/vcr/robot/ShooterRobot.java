package com.soapwaster.vcr.robot;

import com.soapwaster.vcr.behaviour.BehaviourEnum;
import com.soapwaster.vcr.robot_game.Position2D;

public class ShooterRobot extends Robot {
	
	public ShooterRobot(String name) {
		super(name, BehaviourEnum.Shooter);
	}

	public ShooterRobot(String name, Position2D position) {
		super(name, BehaviourEnum.Shooter);
		this.position = position;
	}

	

}
