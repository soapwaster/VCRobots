package com.soapwaster.vcr.stats;

import java.util.List;

import com.soapwaster.vcr.event_handling.StatEnchantEvent;
import com.soapwaster.vcr.robot.Robot;
import com.soapwaster.vcr.robot_game.Game;

public class StatsIncreaser implements Runnable{
	
	private List<Robot> robotPlayers;
	private boolean running = true;
	private long powerupInterval;
	
	public StatsIncreaser(List<Robot> robotPlayers, long powerupInterval) {
		super();
		this.robotPlayers = robotPlayers;
		this.powerupInterval  = powerupInterval;
	}

	@Override
	public void run() {
		
		while(running) {
			int rand = (int) (Math.random() * robotPlayers.size());
			Robot chosenRobot = robotPlayers.get(rand);
		    Stats statEnchanter = new StatsFactory(chosenRobot).getRandomStat();
			
			Game.getInstance().getMainEventDispatcher().addEvent(new StatEnchantEvent(chosenRobot, chosenRobot, statEnchanter));
			
			//Wait a little before gifting the next powerup
			try {
				Thread.sleep(powerupInterval);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void stop() {
		running = false;
	}

}
