package com.soapwaster.vcr.stats;

public abstract class StatDecorator implements IStats{

	protected IStats stat;
	
	@Override
	public String toString() {
		return "range: "+stat.getRange() + ", damage: " + stat.getDamage();
	}
	
}
