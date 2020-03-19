package com.soapwaster.vcr.event_handling;

public interface Listener {
	
	/**
	 * Executes the event passed as parameter
	 * @param event
	 */
	public void execute(Event event);
}
