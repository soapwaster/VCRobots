package com.soapwaster.vcr.robot;

import com.soapwaster.vcr.event_handling.DeadEvent;
import com.soapwaster.vcr.event_handling.Event;
import com.soapwaster.vcr.event_handling.EventTypeEnum;
import com.soapwaster.vcr.event_handling.MoveToEvent;
import com.soapwaster.vcr.event_handling.ShootAtEvent;
import com.soapwaster.vcr.event_handling.StatEnchantEvent;
import com.soapwaster.vcr.robot_game.Game;
import com.soapwaster.vcr.robot_game.Position2D;

public class RobotExecutor {
	private Event event;
	private Robot robot;
	
	public RobotExecutor(Event event, Robot robot) {
		super();
		this.event = event;
		this.robot = robot;
	}


	public void execute() {
		
		if(robot.dead || (event != null && event.getDestination() != robot && event.getDestination() != null)) {
			return;
		}
		
		EventTypeEnum eventName = event.getType(); 
		
		switch (eventName) {
		case MoveTo:{
			MoveToEvent moveEvent = (MoveToEvent) event;
			
			Position2D movePos = moveEvent.getPosition();
			robot.moveTo(movePos);
			robot.notifySupportMovement(movePos);
			
			robot.notifySupportLog("moved to " + moveEvent.getPosition());
			break;
		}
		case ShootAt:{
			ShootAtEvent shootEvent = (ShootAtEvent) event;
			robot.notifySupportShoot(shootEvent.getPosition());
			
			if(robot.receiveDamageIn(shootEvent.getPosition())) {
				robot.decreaseHealth(shootEvent.getDamage());
				robot.notifySupportHit();
				robot.notifySupportLog("got hit by " + event.getSource() + " (" + robot.getHealth()+ " HP left)");
				if(robot.getHealth() <= 0) {
					Game.getInstance().getMainEventDispatcher().addEvent(new DeadEvent(robot, robot));
				}
			}
			break;
		}
		case Dead:{
			Game.getInstance().removeRobot(robot);
			Game.getInstance().getMainEventDispatcher().unregisterListener(robot);
			robot.dead = true;
			
			robot.notifySupportLog("died");
			break;
		}
		case StatEnchant: {
			StatEnchantEvent statEvent = (StatEnchantEvent) event;
			robot.setStat(statEvent.getStatEnchanter());
			robot.notifyStatChange();
			
			robot.notifySupportLog("received " + statEvent.getStatEnchanter().getClass().getSimpleName() + " powerup !");
			break;
		}
		default:
			break;
		}
		
	}
}
