package com.soapwaster.vcr.robot_game;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.soapwaster.vcr.event_handling.GameEventDispatcher;
import com.soapwaster.vcr.event_handling.StopEvent;
import com.soapwaster.vcr.robot.Robot;
import com.soapwaster.vcr.utils.MathUtils;
import com.soapwaster.vcr.view.RobotView2;

public class Game {
	
	private static final Game INSTANCE = new Game();
	public static int MAX_X = 1000;
	public static int MAX_Y = 1000;
	private List<Robot> robotsPlayer;
	private boolean gameEnded = false;
	private StatIncreaser statIncreaser;
    public static Game getInstance() {
        return INSTANCE;
    }

    private final GameEventDispatcher gameEventDispatcher = new GameEventDispatcher();

    public Game() {
        robotsPlayer = new CopyOnWriteArrayList<Robot>();
    }

    public GameEventDispatcher getMainEventDispatcher() {
        return this.gameEventDispatcher;
    }
    
    private Robot getClosestRobotFrom(Robot robot) {
    	int minDistance = Game.MAX_X * Game.MAX_X;
    	Robot closestRobot = null;
		for (Robot robotFoe : robotsPlayer) {
			System.out.println("scanning "+ robotFoe);
			if(robotFoe.equals(robot) || robot.isDead()) {
				continue;
			}
			int distance = MathUtils.distance(robot.getPosition(), robotFoe.getPosition());
			System.out.println("Distance is " + distance + "from " + robot.getPosition() +" to " + robotFoe.getPosition());
			if(distance < minDistance) {
				minDistance = distance;
				closestRobot = robotFoe;
			}
		}
		return closestRobot;
    }
    
    public int getClosestRobotXFrom(Robot robot) {
    	Robot closestRobot = getClosestRobotFrom(robot);
    	if(closestRobot == null) {
    		return -1;
    	}
    	return closestRobot.getPosition().getX();
    }
    
    public int getClosestRobotYFrom(Robot robot) {
    	Robot closestRobot = getClosestRobotFrom(robot);
    	if(closestRobot == null) {
    		return -1;
    	}
    	return closestRobot.getPosition().getY();
    	
    }

	public void addRobot(Robot robot) {
		gameEventDispatcher.addListener(robot);
		robotsPlayer.add(robot);
	}
	
	public void removeRobot(Robot robot){
		robotsPlayer.remove(robot);
		if(robotsPlayer.size() == 1) {
			gameEventDispatcher.stopDispatcher();
			gameEnded = true;
		}
	}
	
	public void startGame() {
		
		RobotView2 view = new RobotView2(robotsPlayer);
		view.show();
		gameEventDispatcher.startDispatcher();
		statIncreaser = new StatIncreaser(robotsPlayer);
		Thread statThread = new Thread(statIncreaser);
		statThread.start();
		while(!gameEnded) {
			for (Robot robot : robotsPlayer) {
				robot.startTurn();
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		System.out.println("\n\n The Game has ended, and the winner is: " + robotsPlayer.get(0));
		
	}
    

}
