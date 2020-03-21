package com.soapwaster.vcr.utils;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.soapwaster.vcr.robot_game.Game;
import com.soapwaster.vcr.robot_game.Position2D;

public class DistanceTest {
	
	@Before
	public void initialize() {
		Game.WIDTH = 1000;
		Game.HEIGHT = 1000;
	}

	@Test
	public void reachableOverflowDistance() {
		Position2D start = new Position2D(900, 900);
		Position2D end = new Position2D(1100, 1100, false);
		
		Position2D reachedPosition = MathUtils.computePositionGivenRange(start, end, 300);
		
		assertEquals(reachedPosition, new Position2D(100, 100));
	}
	
	@Test
	public void nonReachableOverflowDistance() {
		Position2D start = new Position2D(900, 900);
		Position2D end = new Position2D(1100, 1100, false);
		
		Position2D reachedPosition = MathUtils.computePositionGivenRange(start, end, 50);
		
		assertEquals(reachedPosition, new Position2D(935, 935));
	}
	
	@Test
	public void reachableUnderflowDistance() {
		Position2D start = new Position2D(100, 0);
		Position2D end = new Position2D(-100, 0, false);
		
		Position2D reachedPosition = MathUtils.computePositionGivenRange(start, end, 300);
		
		assertEquals(reachedPosition, new Position2D(900, 0));
	}
	
	@Test
	public void nonReachableUnderflowDistance() {
		Position2D start = new Position2D(100, 0);
		Position2D end = new Position2D(-100, 0, false);
		
		Position2D reachedPosition = MathUtils.computePositionGivenRange(start, end, 30);
		
		assertEquals(reachedPosition, new Position2D(70, 0));
	}
	
	@Test
	public void reachableDistance() {
		Position2D start = new Position2D(100, 0);
		Position2D end = new Position2D(200, 0, false);
		
		Position2D reachedPosition = MathUtils.computePositionGivenRange(start, end, 500);
		
		assertEquals(reachedPosition, new Position2D(200, 0));
	}

}
