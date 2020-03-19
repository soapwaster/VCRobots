package com.soapwaster.vcr.behaviour;

import com.soapwaster.vcr.robot.Robot;

public class ShooterBehaviour extends AIBehaviour {

	@Override
	public String getType() {
		return "Shooter";
	}

	public ShooterBehaviour(Robot robot) {
		super(robot);
		this.filename =  "./src/main/resources/robot_ai/shooter.vcr";
	}

}
