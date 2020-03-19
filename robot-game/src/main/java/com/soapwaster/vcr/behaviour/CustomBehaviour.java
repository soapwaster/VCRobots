package com.soapwaster.vcr.behaviour;

import com.soapwaster.vcr.robot.Robot;

public class CustomBehaviour extends AIBehaviour {
	
	@Override
	public String getType() {
		return "custom";
	}
	
	public CustomBehaviour(Robot robot, String filename) {
		super(robot);
		this.filename = filename;
	}

}
