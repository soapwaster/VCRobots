package com.soapwaster.vcr.robot;

import com.soapwaster.vcr.behaviour.BehaviourEnum;
import com.soapwaster.vcr.robot_game.Position2D;

public class DummyRobot extends Robot {
	
	public DummyRobot(String name) {
		super(name, BehaviourEnum.Dummy);
	}

	public DummyRobot(String name, Position2D position) {
		super(name, BehaviourEnum.Dummy);
		this.position = position;
	}

	

}
