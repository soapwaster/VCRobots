package com.soapwaster.vcr.robot_game;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.soapwaster.vcr.event_handling.EventDispatcher;
import com.soapwaster.vcr.robot.RoboRunner;
import com.soapwaster.vcr.robot.Robot;
import com.soapwaster.vcr.stats.StatsIncreaser;
import com.soapwaster.vcr.utils.MathUtils;
import com.soapwaster.vcr.view.RobotGameView;

public class Game {
	
	private static final Game INSTANCE = new Game();
	
	// Game space dimensions
	public static int WIDTH = 1000;
	public static int HEIGHT = 1000;
	
	private boolean gameEnded = false;
	
	private List<Robot> robotPlayers;
	
	private long powerupInterval;
	
	private final EventDispatcher eventDispatcher = new EventDispatcher();
	
    public static Game getInstance() {
        return INSTANCE;
    }

    private Game() {
    	//Instantiate list of Robots. It is concurrent
        robotPlayers = new CopyOnWriteArrayList<Robot>();
    }

    /**
     * Sets game space width and height
     * @param witdh
     * @param height
     */
    public void setGameSize(int witdh, int height) {
    	Game.WIDTH = witdh;
    	Game.HEIGHT = height;
    }
    /**
     * Returns a reference to the EventDispatcher
     * @return
     */
    public EventDispatcher getMainEventDispatcher() {
        return this.eventDispatcher;
    }
    
    /**
     * Adds Robot to the game, and makes the EventDispatcher aware of it
     * @param robot
     */
    public void addRobot(Robot robot) {
		eventDispatcher.registerListener(robot);
		robotPlayers.add(robot);
	}
	
    /**
     * Removes Robot to the game, and makes the EventDispatcher aware of it
     * @param robot
     */
	public void removeRobot(Robot robot){
		robotPlayers.remove(robot);
		if(robotPlayers.size() == 1) {
			eventDispatcher.stopDispatcher();
			gameEnded = true;
		}
	}
	
	 /**
     * Sets up game, in particular:
     * <ul>
     * <li> Robots
     * <li> Positions
     * <li> Game width
     * <li> Game height
     * <li> Power-up interval
     * </ul>
     * @param robots list of robots
     * @param randomPositions true if robots positions should be random
     * @param gameWidth game space width
     * @param gameHeight game space height
     * @param powerupInterval2 
     */
	public void setupGame(List<Robot> robots, boolean randomPositions, int gameWidth, int gameHeight, Integer powerupInterval) {
		for (Robot robot : robots) {
			if(randomPositions) {
				int xRand = (int) (Math.random() * (double) gameWidth);
				int yRand = (int) (Math.random() * (double) gameHeight);
				robot.setPosition(new Position2D(xRand, yRand));
			}
			addRobot(robot);
		}
		Game.WIDTH = gameWidth;
		Game.HEIGHT = gameHeight;
		this.powerupInterval = powerupInterval;
		
	}
	
	/**
	 * Starts the game, in particular the:
	 * <ul>
	 * 	<li> Game View</li>
	 * 	<li> Stats Increaser</li>
	 * 	<li> Event Dispatcher</li>
	 * 	<li> Robots behaviours</li>
	 * </ul>
	 */
	public void startGame() {
		
		StatsIncreaser statsIncreaser = new StatsIncreaser(robotPlayers, powerupInterval);
		List<RoboRunner> roboRunners = new ArrayList<>();
		RobotGameView robotGameView = new RobotGameView(robotPlayers);
		Thread statsThread = new Thread(statsIncreaser);
		List<Thread> roboThreads = new ArrayList<>();
		
		for (Robot robot : robotPlayers) {
			RoboRunner runner = new RoboRunner(robot);
			roboRunners.add(runner);
			roboThreads.add(new Thread(runner));
		}
		
		robotGameView.showBattleGround();
		eventDispatcher.startDispatcher();
		statsThread.start();
		
		for (Thread roboThread : roboThreads) {
			roboThread.start();
		}

		while(!gameEnded) {
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		//stop giving powerups and stop the winner's thread since the game has ended
		statsIncreaser.stop();
		for (RoboRunner roboRunner : roboRunners) {
			roboRunner.stop();
		}
		
		try {
			for (Thread roboThread : roboThreads) {
				roboThread.join();
			}
			statsThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//Notify the view the winning robot
		robotPlayers.get(0).notifySupportWin();
	}
	
	//********************* UTILITY METHODS ***********************//
	
	/**
	 * Returns the Robot closest to the robot passed as parameter
	 * @param robot
	 * @return the closest robot or null, if there are no other robots
	 */
    private Robot getClosestRobotFrom(Robot robot) {
    	int minDistance = Game.WIDTH * Game.WIDTH;
    	Robot closestRobot = null;
		for (Robot robotFoe : robotPlayers) {
			if(robotFoe.equals(robot) || robot.isDead()) {
				continue;
			}
			int distance = MathUtils.distance(robot.getPosition(), robotFoe.getPosition());
			if(distance < minDistance) {
				minDistance = distance;
				closestRobot = robotFoe;
			}
		}
		return closestRobot;
    }
    
    /**
     * Returns the X coordinate in the 2D space of the Robot closest to robot
     * @param robot
     * @return closest robot X coordinate, -1 if no such robot exists
     */
    public int getClosestRobotXFrom(Robot robot) {
    	Robot closestRobot = getClosestRobotFrom(robot);
    	if(closestRobot == null) {
    		return -1;
    	}
    	return closestRobot.getPosition().getX();
    }
    
    /**
     * Returns the Y coordinate in the 2D space of the Robot closest to robot
     * @param robot
     * @return closest robot Y coordinate, -1 if no such robot exists
     */
    public int getClosestRobotYFrom(Robot robot) {
    	Robot closestRobot = getClosestRobotFrom(robot);
    	if(closestRobot == null) {
    		return -1;
    	}
    	return closestRobot.getPosition().getY();
    	
    }

}
