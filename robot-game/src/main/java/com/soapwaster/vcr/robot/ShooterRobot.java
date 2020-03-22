package com.soapwaster.vcr.robot;

import com.soapwaster.vcr.behaviour.BehaviourEnum;
import com.soapwaster.vcr.robot_game.Position2D;
import com.soapwaster.vcr.stats.RobotStats;

public class ShooterRobot extends Robot {

	public ShooterRobot(String name, Position2D position) {
		super(name, BehaviourEnum.Shooter);
		this.position = position;
		this.setStat(new RobotStats(50, 1));
	}

}
