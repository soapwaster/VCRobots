package com.soapwaster.vcr.robot_game;

public class Position2D {
	
	private int x;
	private int y;
	
	public Position2D(int x, int y) {
		this.x = x;
		this.y = y;
		
		this.x = ((x % Game.MAX_X) + Game.MAX_X) % Game.MAX_X;
		this.y = ((y % Game.MAX_Y) + Game.MAX_Y) % Game.MAX_Y;
		
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
	
	public void normalize() {
		x = ((x % Game.MAX_X) + Game.MAX_X) % Game.MAX_X;
		y = ((y % Game.MAX_Y) + Game.MAX_Y) % Game.MAX_Y;
	}

	@Override
	public String toString() {
		return "[" + x + "," + y + "]"; 
	}
	

}
