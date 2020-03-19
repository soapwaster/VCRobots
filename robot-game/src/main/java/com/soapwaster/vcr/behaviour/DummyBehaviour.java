package com.soapwaster.vcr.behaviour;

import com.soapwaster.vcr.robot.Robot;

public class DummyBehaviour extends AIBehaviour {

	@Override
	public String getType() {
		return "Dummy";
	}

	public DummyBehaviour(Robot robot) {
		super(robot);
		this.filename =  "./src/main/resources/robot_ai/dummy.vcr";
	}

}
