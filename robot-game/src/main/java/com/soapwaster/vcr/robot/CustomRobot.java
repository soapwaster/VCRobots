package com.soapwaster.vcr.robot;

import java.io.IOException;
import com.soapwaster.vcr.behaviour.BehaviourFactory;
import com.soapwaster.vcr.behaviour.BehaviourFactoryException;
import com.soapwaster.vcr.robot_game.Position2D;

public class CustomRobot extends Robot {
	
	public CustomRobot(String name, String behaviourFilename) {
		super(name);
		try {
			behaviour = new BehaviourFactory(this).getCustomBehaviour(behaviourFilename);
		} catch (IOException e) {
			System.err.println("\nThere was an error with your file. Check its path. Using Default behaviour");
		} catch (BehaviourFactoryException e) {
			e.printStackTrace();
		}
	}

	public CustomRobot(String name, String behaviourFilename, Position2D position) {
		this(name, behaviourFilename);
		this.position = position;
	}

	

}
