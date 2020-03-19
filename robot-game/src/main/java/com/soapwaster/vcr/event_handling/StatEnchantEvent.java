package com.soapwaster.vcr.event_handling;

import com.soapwaster.vcr.stats.Stats;

public class StatEnchantEvent extends Event {
	
	Stats statEnchanter;
	
	public StatEnchantEvent(Listener source, Listener destination, Stats statEnchanter) {
		super(source, destination);
		this.statEnchanter = statEnchanter;
	}
	
	public Stats getStatEnchanter() {
		return statEnchanter;
	}

	@Override
	public EventTypeEnum getType() {
		return EventTypeEnum.StatEnchant;
	}

}
