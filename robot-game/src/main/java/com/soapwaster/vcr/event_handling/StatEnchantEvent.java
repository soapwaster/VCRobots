package com.soapwaster.vcr.event_handling;

import com.soapwaster.vcr.stats.IStats;

public class StatEnchantEvent extends Event {
	
	IStats statEnchanter;
	
	public StatEnchantEvent(Listener source, Listener destination, IStats statEnchanter) {
		super(source, destination);
		this.statEnchanter = statEnchanter;
	}
	
	public IStats getStatEnchanter() {
		return statEnchanter;
	}

}
