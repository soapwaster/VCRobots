package com.soapwaster.vcr.robot_game;

import com.soapwaster.vcr.behaviour.BehaviourEnum;
import com.soapwaster.vcr.event_handling.DeadEvent;
import com.soapwaster.vcr.event_handling.Event;
import com.soapwaster.vcr.event_handling.MoveToEvent;
import com.soapwaster.vcr.robot.ConcreteRobot;
import com.soapwaster.vcr.robot.Robot;
import com.soapwaster.vcr.stats.AimEnchanterDecorator;
import com.soapwaster.vcr.stats.IStats;
import com.soapwaster.vcr.stats.MissledDamageDecorator;
import com.soapwaster.vcr.utils.MathUtils;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	Robot r = new ConcreteRobot("Valerio", BehaviourEnum.Default, new Position2D(0,10));
    	Robot r2 = new ConcreteRobot("Alessio", BehaviourEnum.Default, new Position2D(0,500));
    	Robot r3 = new ConcreteRobot("Nicola", BehaviourEnum.Default, new Position2D(700,400));
    	Robot r4 = new ConcreteRobot("Federica", BehaviourEnum.Default, new Position2D(800,20));
  
    	Game.getInstance().addRobot(r);
    	Game.getInstance().addRobot(r2);
    	Game.getInstance().addRobot(r3);
    	Game.getInstance().addRobot(r4);
    	
    
    	Game.getInstance().startGame();
   
    }
}
