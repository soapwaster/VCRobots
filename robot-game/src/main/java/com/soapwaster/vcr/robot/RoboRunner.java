package com.soapwaster.vcr.robot;

public class RoboRunner implements Runnable {
	
	private Robot robot;
	private boolean stop = false;
	
	public RoboRunner(Robot robot) {
		super();
		this.robot = robot;
	}

	@Override
	public void run() {
		while(!robot.isDead() && !stop) {
			robot.runBehaviour();
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void stop() {
		stop = true;
	}
}
