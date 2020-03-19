package com.soapwaster.vcr.view;

import java.awt.Color;

import javax.swing.JLabel;

import com.soapwaster.vcr.robot_game.Position2D;

public class RobotViewStats {
	
	private JLabel hpLabel;
	private JLabel rangeLabel;
	private JLabel damageLabel;
	private Position2D lastHitmark;
	private Color color;
	
	public RobotViewStats() {
		lastHitmark = new Position2D(-1, -1, false);
		this.color = Color.BLACK;
	}	
	
	public JLabel getHpLabel() {
		return hpLabel;
	}
	public void setHpLabel(JLabel hpLabel) {
		this.hpLabel = hpLabel;
	}
	public Position2D getLastHitmark() {
		return lastHitmark;
	}
	public void setLastHitmark(Position2D lastHitmark) {
		this.lastHitmark = lastHitmark;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public JLabel getRangeLabel() {
		return rangeLabel;
	}
	
	public void setRangeLabel(JLabel rangeLabel) {
		this.rangeLabel = rangeLabel;
	}
	
	public void setDamageLabel(JLabel damageLabel) {
		this.damageLabel = damageLabel;
	}
	
	public JLabel getDamageLabel() {
		return damageLabel;
	}
	

	
}
