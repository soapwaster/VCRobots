package com.soapwaster.vcr.stats;

/**
 * Decorator that improves the damage statistics only
 */
public class BubbleBlastDamageDecorator extends StatsDecorator{
	
	public BubbleBlastDamageDecorator(Stats stat) {
		this.stat = stat;
	}

	@Override
	public double getDamage() {
		return 4 + stat.getDamage();
	}

	@Override
	public int getRange() {
		return stat.getRange();
	}

}
