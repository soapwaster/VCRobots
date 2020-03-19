package com.soapwaster.vcr.stats;

public class BubbleBlastDamageDecorator extends StatDecorator{
	
	public BubbleBlastDamageDecorator(IStats stat) {
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
