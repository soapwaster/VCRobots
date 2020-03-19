package com.soapwaster.vcr.utils;
import com.soapwaster.vcr.robot_game.Position2D;

public class MathUtils {

	/**
	 * Returns the euclidean distance between two points in a 2D space
	 * @param from first point
	 * @param to second point
	 * @return euclidean distance
	 */
	public static int distance(Position2D from, Position2D to) {
		
	    int x1 = from.getX();
	    int x2 = to.getX();
	    int y1 = from.getY();
	    int y2 = to.getY();
	    
	    int distance = (int) Math.sqrt((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1));
	    
	    return distance;
	}
	
	/**
	 * Returns the direction angle if an agent wants to go from a point to another
	 * @param from initial position
	 * @param to target position
	 * @return the angle between the two vectors
	 */
	public static double directionAngle(Position2D from, Position2D to) {
		
		double x1 = from.getX();
	    double x2 = to.getX();
	    double y1 = from.getY();
	    double y2 = to.getY();
	    
		return Math.atan2(y2-y1,x2-x1);
	}
	
	/**
	 * Returns the point in the 2D space reachable from an initial position with a given range
	 * <ul>
	 * 	<li> If the second point cannot be reached within the range, then the farthest point is returned
	 *  <li> If it can be reached, then it is simply returned, normalized if it exceeds game board size
	 * </ul>
	 * @param from first point
	 * @param to second point
	 * @param range amount of distance 
	 * @return the farthest reachable point from the first point, with that range
	 */
	public static Position2D computePositionGivenRange(Position2D from, Position2D to, int range) {
		
		double directionAngle = MathUtils.directionAngle(from, to);
		
		//find the two components of the 2D vector of magnitude range having an angle of directionAngle
		double finalX = (double) from.getX() + (Math.cos(directionAngle) * (double) range);
		double finalY = (double) from.getY() + (Math.sin(directionAngle) * (double) range);
		
//		System.out.println("Want to get " + to + " with " + range + " from " + from);
		
		Position2D finalPosition = new Position2D((int) finalX, (int) finalY);
		
		//non normalized position, since distance finds the non normalized distance 
		Position2D nonNormalizedPosition = new Position2D((int) finalX, (int) finalY, false);
		
		//the second point is reachable within the range
		if(distance(from, to) < distance(from, nonNormalizedPosition)) {
			return to;
		}
		
		return finalPosition;

	}
}
