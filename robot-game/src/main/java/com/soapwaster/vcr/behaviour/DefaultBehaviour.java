package com.soapwaster.vcr.behaviour;

import com.soapwaster.vcr.robot.Robot;

public class DefaultBehaviour extends AIBehaviour {

	@Override
	public String getType() {
		return "Default";
	}

	public DefaultBehaviour(Robot robot) {
		super(robot);
		this.filename = "src/main/resources/robot_ai/default.vcr";
	}

}
