package com.soapwaster.vcr.robot_game;

import org.junit.Test;

public class AllDefault {
	
	
	@Test
	public void allDefaultRandom() throws InterruptedException {
		String[] args = new String[12];
		String r1 = "Valerio,Default,10,10";
		String r2 = "Manolo,Default,900,900";
		String r3 = "Priscilla,src/main/resources/robot_ai/hider.vcr,900,60";
		String r4 = "Olimpodeo,Default,50,900";
		String sizex = "1000";
		String sizey = "1000";
		String random = "true";
		String interval = "1000";
		
		args[0] = "-r";
		args[1] = r1;
		args[2] = r2;
		args[3] = r3;
		args[4] = r4;
		args[5] = "-gs";
		args[6] = sizex;
		args[7] = sizey;
		args[8] = "-rp";
		args[9] = random;
		args[10] =  "-pi";
		args[11] = interval;
		
		RobotBattle.main(args);	
		
		while (true) { Thread.sleep(2000); }
	}

}
