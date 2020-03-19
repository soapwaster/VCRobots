package com.soapwaster.vcr.stats;

public class AimEnchanterDecorator extends StatDecorator{
	
	public AimEnchanterDecorator(IStats stat) {
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
