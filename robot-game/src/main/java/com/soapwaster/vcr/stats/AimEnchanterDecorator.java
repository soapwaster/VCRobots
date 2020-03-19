package com.soapwaster.vcr.stats;

/**
 * Decorator that improves the range statistics only
 */
public class AimEnchanterDecorator extends StatsDecorator{
	
	public AimEnchanterDecorator(Stats stat) {
		this.stat = stat;
	}

	@Override
	public double getDamage() {
		return stat.getDamage();
	}

	@Override
	public int getRange() {
		return 5 + stat.getRange();
	}

}
