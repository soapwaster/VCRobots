package com.soapwaster.vcr.robot;

import com.soapwaster.vcr.behaviour.BehaviourEnum;
import com.soapwaster.vcr.robot_game.Position2D;
import com.soapwaster.vcr.stats.RobotStats;

public class DefaultRobot extends Robot {

	public DefaultRobot(String name, Position2D position) {
		super(name, BehaviourEnum.Default);
		this.position = position;
		this.setStat(new RobotStats(30, 5));
	}

}
