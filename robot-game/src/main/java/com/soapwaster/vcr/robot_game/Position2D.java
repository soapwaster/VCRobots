package com.soapwaster.vcr.robot_game;

/**
 * Represents a point in the 2D space of the game
 *
 */
public class Position2D {
	
	private int x;
	private int y;
	
	public Position2D(int x, int y) {
		this.x = x;
		this.y = y;
		
		normalize();
		
	}
	
	public Position2D(int x, int y, boolean normalize) {
		this.x = x;
		this.y = y;
		
		if(normalize) {
			normalize();
		}
	}

	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	/**
	 * Normalizes the point, so that if it exceeds game limits, it just restart from 0. Makes it circular
	 */
	public void normalize() {
		x = ((x % Game.MAX_X) + Game.MAX_X) % Game.MAX_X;
		y = ((y % Game.MAX_Y) + Game.MAX_Y) % Game.MAX_Y;
	}

	@Override
	public String toString() {
		return "[" + x + "," + y + "]"; 
	}
	

}
