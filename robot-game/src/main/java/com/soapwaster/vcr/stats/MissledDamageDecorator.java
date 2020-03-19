package com.soapwaster.vcr.stats;

public class MissledDamageDecorator extends StatDecorator{
	
	public MissledDamageDecorator(IStats iStats) {
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
