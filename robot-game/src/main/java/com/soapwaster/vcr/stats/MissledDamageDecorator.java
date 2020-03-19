package com.soapwaster.vcr.stats;

/**
 * Decorator that improves both range and damage statistics
 */
public class MissledDamageDecorator extends StatsDecorator{
	
	public MissledDamageDecorator(Stats iStats) {
		this.stat = iStats;
	}

	@Override
	public double getDamage() {
		return 2 + stat.getDamage();
	}

	@Override
	public int getRange() {
		return 2 + stat.getRange();
	}
	
}
