package com.soapwaster.vcr.robot_game;

import java.util.List;

import com.soapwaster.vcr.event_handling.StatEnchantEvent;
import com.soapwaster.vcr.robot.Robot;
import com.soapwaster.vcr.stats.AimEnchanterDecorator;
import com.soapwaster.vcr.stats.IStats;
import com.soapwaster.vcr.stats.StatsFactory;

public class StatIncreaser implements Runnable{
	
	private List<Robot> robotPlayers;
	
	public StatIncreaser(List<Robot> robotPlayers) {
		super();
		this.robotPlayers = robotPlayers;
	}

	@Override
	public void run() {
		
		while(true) {
			int rand = (int) (Math.random() * robotPlayers.size());
			Robot chosenRobot = robotPlayers.get(rand);
		
			IStats statEnchanter = new StatsFactory(chosenRobot).getRandomStat();
			
			Game.getInstance().getMainEventDispatcher().addEvent(new StatEnchantEvent(chosenRobot, chosenRobot, statEnchanter));
			
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
