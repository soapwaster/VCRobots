package com.soapwaster.vcr.robot;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;

import com.soapwaster.vcr.behaviour.AIBehaviour;
import com.soapwaster.vcr.behaviour.BehaviourEnum;
import com.soapwaster.vcr.behaviour.BehaviourFactory;
import com.soapwaster.vcr.behaviour.BehaviourFactoryException;
import com.soapwaster.vcr.event_handling.Event;
import com.soapwaster.vcr.event_handling.Listener;
import com.soapwaster.vcr.robot_game.Game;
import com.soapwaster.vcr.robot_game.Position2D;
import com.soapwaster.vcr.stats.RobotStats;
import com.soapwaster.vcr.stats.Stats;
import com.soapwaster.vcr.utils.MathUtils;
import com.soapwaster.vcr.view.PropertyChangeType;

public abstract class Robot implements Listener{
	
	protected AIBehaviour behaviour;
	protected Position2D position;
	private PropertyChangeSupport support;
	private Stats stats;
	protected double health;
	private String name;
	boolean dead = false;
	
	/**
	 * Default constructor. Sets all value by default
	 * <ul>
	 * 	<li> Health : 100 HP</li>
	 * 	<li> Damage : 5 units</li>
	 * 	<li> Range  : 30 units</li>
	 * 	<li> Position  : Center of the game space</li>
	 * 	<li> Behaviour : Default Behaviour </li>
	 * </ul>
	 * @param name Robot name
	 */
	public Robot(String name){
		this.name = name;
		this.health = 100;
		this.support = new PropertyChangeSupport(this);
		this.stats = new RobotStats(30, 5);
		this.position = new Position2D(Game.WIDTH / 2, Game.WIDTH / 2);
		try {
			behaviour = new BehaviourFactory(this).getBehaviour(BehaviourEnum.Default);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (BehaviourFactoryException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Sets all default values but the behaviour which can be decided
	 * @param name Robot name
	 * @param behaviourType behaviour type
	 */
	public Robot(String name, BehaviourEnum behaviourType){
		this(name);
		try {
			behaviour = new BehaviourFactory(this).getBehaviour(behaviourType);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (BehaviourFactoryException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets all default values but the behaviour and the position which can be decided
	 * @param name Robot name
	 * @param behaviourType behaviour type
	 * @param position in game position
	 */
	public Robot(String name, BehaviourEnum behaviourType, Position2D position){
		this(name, behaviourType);
		this.position = position;
	}
	
	public void setStat(Stats s) {
		this.stats = s;
	}
	
	public Stats getStat() {
		return stats;
	}
	
	public void setPosition(Position2D pos) {
		this.position = pos;
	}
	
	public Position2D getPosition() {
		return position;
	}
	
	public double getHealth() {
		return health;
	}
	
	public boolean isDead() {
		return dead;
	}
	
	/**
	 * Starts performing Robot's behaviour
	 */
	public void runBehaviour() {
		behaviour.startTurn();
	}
	
	public void decreaseHealth(double decreaseAmount) {
		health -= decreaseAmount;
		if(health <= 0) {
			health = 0;
		}
	}
	
	/**
	 * Moves the robot to the desired position. It gets normalized if it is not
	 * @param position desired position
	 */
	public void moveTo(Position2D position) {
		this.position = new Position2D(position.getX(), position.getY());
	}
	
	/**
	 * Checks whether a target position is within personal range
	 * @param targetPosition
	 * @return true if it reachable, false otherwise
	 */
	public boolean inRange(Position2D targetPosition) {
		return MathUtils.distance(position, targetPosition) <= stats.getRange();
	}
	
	/**
	 * Returns whether the robot would receive damage if hit in hitPosition radius
	 * @param hitPosition 
	 * @return true if robot is in hitPosition radius, false otherwise
	 */
	boolean receiveDamageIn(Position2D hitPosition) {
		int hitRadius = 10;
		int currentX = getPosition().getX();
		int currentY = getPosition().getY();
		
		//checks a 10 units radius around the position, to see whether the player is inside it
		if(currentX-hitRadius < hitPosition.getX() && hitPosition.getX() < currentX+hitRadius && 
				currentY-hitRadius < hitPosition.getY() && hitPosition.getY() < currentY+hitRadius) {
			return true;
		}
		return false;
	}
	
	@Override
	public void execute(Event e) {
		new RobotExecutor(e, this).execute();
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	/**
	 * Adds a listener to the PropertyChangeListener
	 * @param pcl
	 */
	public void addPropertyChangeListener(PropertyChangeListener pcl) {
	    support.addPropertyChangeListener(pcl);
	}
	
	/**
	 * Removes a listener to the PropertyChangeListener
	 * @param pcl
	 */
	public void removePropertyChangeListener(PropertyChangeListener pcl) {
	    support.removePropertyChangeListener(pcl);
	}
	
	/**
	 * Notifies all PropertyChangeListeners when the Robot moves
	 * @param movePosition the position the Robot has moved
	 */
	public void notifySupportMovement(Position2D movePosition) {
        support.firePropertyChange(PropertyChangeType.Move.toString(), new Position2D(-1,-1) , movePosition);
    }
	
	/**
	 * Notifies all PropertyChangeListeners when some Robot has shot
	 * @param shootPosition the position of the shot
	 */
	public void notifySupportShoot(Position2D shootPosition) {
        support.firePropertyChange(PropertyChangeType.Shoot.toString(), new Position2D(-1,-1) , shootPosition);
    }
	
	/**
	 * Notifies all PropertyChangeListeners when the Robot has been hit
	 */
	public void notifySupportHit() {
        support.firePropertyChange(PropertyChangeType.Hit.toString(), 0, this.getHealth());
    }

	/**
	 * Notifies all PropertyChangeListeners when the Robot stats have been updated
	 */
	public void notifyStatChange() {
		support.firePropertyChange(PropertyChangeType.Stats.toString(), 0, this.getHealth());
	}
	
	/**
	 * Notifies all PropertyChangeListeners that this Robot has won the game
	 */
	public void notifySupportWin() {
		support.firePropertyChange(PropertyChangeType.Win.toString(), 0, this);
	}

	/**
	 * Notifies all PropertyChangeListeners about some log event
	 */
	public void notifySupportLog(String logDescription) {
		support.firePropertyChange(PropertyChangeType.Log.toString(), 0, logDescription);
	}
	
	
}
