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
			System.out.println(robot.toString() + " moved to " + moveEvent.getPosition());
			break;
		}
		case ShootAt:{
			
			ShootAtEvent shootEvent = (ShootAtEvent) event;
			
			robot.notifySupportShoot(shootEvent.getPosition());
			if(robot.receiveDamageIn(shootEvent.getPosition())) {
				robot.decreaseHealth(shootEvent.getDamage());
				robot.notifySupportHit();
				if(robot.getHealth() <= 0) {
					Game.getInstance().getMainEventDispatcher().addEvent(new DeadEvent(robot, robot));
				}
			}
			System.out.println(robot + " " + robot.getPosition()+" : " + event.getSource() + " shot at " + shootEvent.getPosition());
			break;
		}
		case Dead:{
			
			System.out.println(robot + " just died");
			Game.getInstance().removeRobot(robot);
			Game.getInstance().getMainEventDispatcher().unregisterListener(robot);
			robot.dead = true;
			break;
		}
		case StatEnchant: {
			StatEnchantEvent statEvent = (StatEnchantEvent) event;
			robot.setStat(statEvent.getStatEnchanter());
			robot.notifyStatChange();
			
			System.out.println(robot + " : receiving powerup, " + robot.getStat());
			break;
		}
		default:
			break;
		}
		
	}
}
