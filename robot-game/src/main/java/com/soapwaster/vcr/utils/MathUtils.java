package com.soapwaster.vcr.utils;
import com.soapwaster.vcr.robot_game.Game;
import com.soapwaster.vcr.robot_game.Position2D;

import jdk.jfr.Unsigned;

public class MathUtils {

	public static int distance(Position2D from, Position2D to) {
		
	    int x1 = from.getX();
	    int x2 = to.getX();
	    int y1 = from.getY();
	    int y2 = to.getY();
	    
	    int distance = (int) Math.sqrt((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1));
	    
	    return distance;
	}
	
	public static double degree(Position2D from, Position2D to) {
		
		double x1 = from.getX();
	    double x2 = to.getX();
	    double y1 = from.getY();
	    double y2 = to.getY();
	    
		return Math.atan2(y2-y1,x2-x1);
	}
	
	public static Position2D computePositionGivenRange(Position2D from, Position2D to, int range) {
		
		double degree = MathUtils.degree(from, to);
		double finalX = (double) from.getX() + (Math.cos(degree) * (double) range);
		double finalY = (double) from.getY() + (Math.sin(degree) * (double) range);
		
		System.out.println("Want to get " + to + " with " + range + " from " + from);
		
		Position2D finalPosition = new Position2D((int) finalX, (int) finalY);
		
		Position2D nonNormalizedPosition = new Position2D((int) finalX, (int) finalY, false);
		
		if(distance(from, to) < distance(from, nonNormalizedPosition)) {
			return to;
		}
		
		return finalPosition;

	}
}
