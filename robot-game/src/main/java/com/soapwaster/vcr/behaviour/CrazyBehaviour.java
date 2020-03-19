package com.soapwaster.vcr.behaviour;

import com.soapwaster.vcr.robot.Robot;

public class CrazyBehaviour extends AIBehaviour {

	@Override
	public String getType() {
		return "Crazy";
	}

	public CrazyBehaviour(Robot robot) {
		super(robot);
		this.filename = "./src/main/resources/robot_ai/test.vcr";
	}

}
