package com.soapwaster.vcr.stats;

public abstract class StatsDecorator implements Stats{

	protected Stats stat;
	
	@Override
	public String toString() {
		return "range: "+stat.getRange() + ", damage: " + stat.getDamage();
	}
	
}
