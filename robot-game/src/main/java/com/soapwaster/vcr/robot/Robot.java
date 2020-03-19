package com.soapwaster.vcr.robot;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;

import com.soapwaster.vcr.behaviour.AIBehaviour;
import com.soapwaster.vcr.behaviour.BehaviourEnum;
import com.soapwaster.vcr.behaviour.BehaviourFactory;
import com.soapwaster.vcr.behaviour.BehaviourFactoryException;
import com.soapwaster.vcr.event_handling.DeadEvent;
import com.soapwaster.vcr.event_handling.Event;
import com.soapwaster.vcr.event_handling.Listener;
import com.soapwaster.vcr.robot_game.Game;
import com.soapwaster.vcr.robot_game.Position2D;
import com.soapwaster.vcr.stats.ConcreteStats;
import com.soapwaster.vcr.stats.IStats;
import com.soapwaster.vcr.utils.MathUtils;

import jdk.nashorn.api.tree.NewTree;

public abstract class Robot implements Listener{
	
	private AIBehaviour behaviour;
	protected Position2D position;
	private PropertyChangeSupport support;
	private IStats stat;
	private double health = 100.0;
	private String name;
	boolean dead = false;
	
	public Robot(String name){
		support = new PropertyChangeSupport(this);
		this.name = name;
		stat = new ConcreteStats(30, 40);
		position = new Position2D(15, 15);
		try {
			behaviour = new BehaviourFactory(this).getBehaviour(BehaviourEnum.Default);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (BehaviourFactoryException e) {
			e.printStackTrace();
		}
		
	}
	
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
	
	public void startTurn() {
		behaviour.startTurn();
	}
	
	public void setStat(IStats s) {
		this.stat = s;
	}
	
	public IStats getStat() {
		return stat;
	}
	
	public void decreaseHealth(double decreaseAmount) {
		health -= decreaseAmount;
		if(health <= 0) {
			health = 0;
		}
	}
	
	public void moveTo(Position2D position) {
		Position2D newPosition = position;
		
		//compute new position. If it hits the wall, get out from the other side.
		newPosition = new Position2D(position.getX(), position.getY());
		
		System.out.println(this + " moved to " + newPosition);
		this.position = newPosition;
	}
	
	public boolean inRange(Position2D targetPosition) {
		return MathUtils.distance(position, targetPosition) <= stat.getRange();
	}
	
	public void shootAt(Position2D position) {
		this.position = position;
	}
	
	boolean receiveDamageIn(Position2D hitPosition) {
		int currentX = getPosition().getX();
		int currentY = getPosition().getY();
		
		System.out.println("Trying to get damage in " + hitPosition);
		if(currentX-10 < hitPosition.getX() && hitPosition.getX() < currentX+10 && 
				currentY-10 < hitPosition.getY() && hitPosition.getY() < currentY+10) {
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
	
	public Position2D getPosition() {
		return position;
	}
	
	public double getHealth() {
		return health;
	}

	public boolean isDead() {
		return dead;
	}
	
	public void addPropertyChangeListener(PropertyChangeListener pcl) {
	    support.addPropertyChangeListener(pcl);
	}
	 
	public void removePropertyChangeListener(PropertyChangeListener pcl) {
	    support.removePropertyChangeListener(pcl);
	}
	
	public void notifySupportMovement(Position2D movePosition) {
        support.firePropertyChange("move", new Position2D(-1,-1) , movePosition);
    }
	
	public void notifySupportShoot(Position2D shootPosition) {
        support.firePropertyChange("shoot", new Position2D(-1,-1) , shootPosition);
    }
	
	public void notifySupportHit() {
        support.firePropertyChange("hit", 0, this.getHealth());
    }

	public void notifyStatChange() {
		support.firePropertyChange("stat", 0, this.getHealth());
	}
	
	
}
