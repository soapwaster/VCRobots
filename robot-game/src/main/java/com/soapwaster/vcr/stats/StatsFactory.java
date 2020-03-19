package com.soapwaster.vcr.stats;

import com.soapwaster.vcr.robot.Robot;

public class StatsFactory {

	private Robot robot;
    
    public StatsFactory(Robot robot) {
    	this.robot = robot;
    }
    
    public Stats getRandomStat (){
        Stats statEnchanter = null;
        
        int rand = (int) (Math.random() * 3);
        switch (rand){
            case 0:
                statEnchanter = new AimEnchanterDecorator(robot.getStat());
                break;
            case 1:
                statEnchanter = new BubbleBlastDamageDecorator(robot.getStat());
                break;
            case 2:
            	statEnchanter = new MissledDamageDecorator(robot.getStat());
            	break;
        }
        return statEnchanter;
    }
}
