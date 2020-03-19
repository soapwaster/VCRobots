package com.soapwaster.vcr.stats;

public class ConcreteStats implements IStats{
	
	private int range;
	private double damage;
	
	public ConcreteStats(int range, double damage) {
		super();
		this.range = range;
		this.damage = damage;
	}

	@Override
	public int getRange() {
		return range;
	}

	@Override
	public double getDamage() {
		return damage;
	}
	
	@Override
	public String toString() {
		return "range: "+range + ", damage: " + damage;
	}
}
