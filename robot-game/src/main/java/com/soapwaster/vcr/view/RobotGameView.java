package com.soapwaster.vcr.view;


import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import java.awt.FlowLayout;
import java.awt.Graphics;

import javax.swing.SwingConstants;

import com.soapwaster.vcr.robot.Robot;
import com.soapwaster.vcr.robot_game.Game;
import com.soapwaster.vcr.robot_game.Position2D;
import javax.swing.JSeparator;
import javax.swing.border.LineBorder;

public class RobotView2 implements PropertyChangeListener{

	private JFrame frame;
	private List<Robot> robots;
	private Map<Robot, RobotViewStats> robotViewStats;
	private RobotViewPanel panel_2;

	/**
	 * Create the application.
	 */
	public RobotView2(List<Robot> robots) {
		this.robots = robots;
		robotViewStats = new HashMap<Robot, RobotViewStats>();
		for (Robot robot : robots) {
			RobotViewStats rvs = new RobotViewStats();
			JLabel hpLabel = new JLabel(String.valueOf(robot.getHealth()));
			JLabel rangeLabel = new JLabel(String.valueOf(robot.getStat().getRange()));
			JLabel damageLabel = new JLabel(String.valueOf(robot.getStat().getDamage()));
			rvs.setHpLabel(hpLabel);
			rvs.setDamageLabel(damageLabel);
			rvs.setRangeLabel(rangeLabel);
			robotViewStats.put(robot, rvs);
			robot.addPropertyChangeListener(this);
		}
		robotViewStats.get(robots.get(0)).setColor(Color.BLACK);
		robotViewStats.get(robots.get(1)).setColor(Color.BLUE);
		robotViewStats.get(robots.get(2)).setColor(Color.GREEN);
		robotViewStats.get(robots.get(3)).setColor(Color.RED);
		
		panel_2 = new RobotViewPanel(robotViewStats);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(panel_1, BorderLayout.EAST);
		panel_1.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new LineBorder(Color.GRAY));
		panel_1.add(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JLabel lblRn = new JLabel(robots.get(0).toString());
		lblRn.setForeground(robotViewStats.get(robots.get(0)).getColor());
		lblRn.setHorizontalAlignment(SwingConstants.CENTER);
		panel_3.add(lblRn, BorderLayout.NORTH);
		
		JPanel panel_7 = new JPanel();
		panel_3.add(panel_7, BorderLayout.CENTER);
		panel_7.setLayout(new GridLayout(3, 2, 0, 0));
		
		JLabel lblHp = new JLabel("Hp: ");
		panel_7.add(lblHp);
		
		panel_7.add(robotViewStats.get(robots.get(0)).getHpLabel());
		
		JLabel lblNewLabel_2 = new JLabel("Range:");
		panel_7.add(lblNewLabel_2);
		
		panel_7.add(robotViewStats.get(robots.get(0)).getRangeLabel());
		
		JLabel lblDamage = new JLabel("Damage:");
		panel_7.add(lblDamage);
		
		panel_7.add(robotViewStats.get(robots.get(0)).getDamageLabel());
		
		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new LineBorder(Color.GRAY));
		panel_1.add(panel_5);
		panel_5.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_4 = new JPanel();
		panel_5.add(panel_4, BorderLayout.CENTER);
		panel_4.setLayout(new GridLayout(3, 2, 0, 0));
		
		JLabel lblNewLabel_1 = new JLabel("Hp: ");
		panel_4.add(lblNewLabel_1);
		
		panel_4.add(robotViewStats.get(robots.get(1)).getHpLabel());
		
		JLabel lblNewLabel_5 = new JLabel("Range:");
		panel_4.add(lblNewLabel_5);
		
		panel_4.add(robotViewStats.get(robots.get(1)).getRangeLabel());
		
		
		JLabel lblNewLabel_6 = new JLabel("Damage:");
		panel_4.add(lblNewLabel_6);
		
		panel_4.add(robotViewStats.get(robots.get(1)).getDamageLabel());
		
		JLabel lblNewLabel = new JLabel(robots.get(1).toString());
		lblNewLabel.setForeground(robotViewStats.get(robots.get(1)).getColor());
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel_5.add(lblNewLabel, BorderLayout.NORTH);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBorder(new LineBorder(Color.GRAY));
		panel_1.add(panel_6);
		panel_6.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_3 = new JLabel(robots.get(2).toString());
		lblNewLabel_3.setForeground(robotViewStats.get(robots.get(2)).getColor());
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		panel_6.add(lblNewLabel_3, BorderLayout.NORTH);
		
		JPanel panel_8 = new JPanel();
		panel_6.add(panel_8);
		panel_8.setLayout(new GridLayout(3, 2, 0, 0));
		
		
		JLabel lblHp_1 = new JLabel("Hp: ");
		panel_8.add(lblHp_1);
		
		
		panel_8.add(robotViewStats.get(robots.get(2)).getHpLabel());
		
		JLabel lblNewLabel_8 = new JLabel("Range:");
		panel_8.add(lblNewLabel_8);
		
		panel_8.add(robotViewStats.get(robots.get(2)).getRangeLabel());
		
		JLabel lblNewLabel_9 = new JLabel("Damage:");
		panel_8.add(lblNewLabel_9);
		
		panel_8.add(robotViewStats.get(robots.get(2)).getDamageLabel());
		
		JPanel panel_9 = new JPanel();
		panel_9.setBorder(new LineBorder(Color.GRAY));
		panel_1.add(panel_9);
		panel_9.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_4 = new JLabel(robots.get(3).toString());
		lblNewLabel_4.setForeground(robotViewStats.get(robots.get(3)).getColor());
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		panel_9.add(lblNewLabel_4, BorderLayout.NORTH);
		
		JPanel panel_10 = new JPanel();
		panel_9.add(panel_10);
		panel_10.setLayout(new GridLayout(3, 2, 0, 0));
		
		JLabel lblHp_2 = new JLabel("Hp: ");
		panel_10.add(lblHp_2);
		
		panel_10.add(robotViewStats.get(robots.get(3)).getHpLabel());
		
		JLabel lblNewLabel_10 = new JLabel("Range:");
		panel_10.add(lblNewLabel_10);
		
		panel_10.add(robotViewStats.get(robots.get(3)).getRangeLabel());
		
		JLabel lblNewLabel_11 = new JLabel("Damage:");
		panel_10.add(lblNewLabel_11);
		
		panel_10.add(robotViewStats.get(robots.get(3)).getDamageLabel());
		
	
		frame.setSize(new Dimension(700, 400));
		frame.getContentPane().add(panel_2, BorderLayout.CENTER);
		
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String pName = evt.getPropertyName();
		switch (pName) {
		case "hit":{
			for (Robot robot : robots) {
				robotViewStats.get(robot).getHpLabel().setText(String.valueOf(robot.getHealth()));
			}
			break;	
		}
		case "stat":{
			for (Robot robot : robots) {
				robotViewStats.get(robot).getRangeLabel().setText(String.valueOf(robot.getStat().getRange()));
				robotViewStats.get(robot).getDamageLabel().setText(String.valueOf(robot.getStat().getDamage()));

			}
			break;	
		}
		case "move":{
			panel_2.revalidate();
			panel_2.repaint();
			
			break;	
		}
		case "shoot":{
			Position2D shootPos = (Position2D) evt.getNewValue();
			Robot source = (Robot) evt.getSource();
			robotViewStats.get(source).setLastHitmark(new Position2D(shootPos.getX(), shootPos.getY()));
			panel_2.revalidate();
			panel_2.repaint();
			break;	
		}

		default:
			break;
		}
		
	}
	
	public void show() {
		frame.setVisible(true);
	}
	

}

class RobotViewPanel extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Map<Robot, RobotViewStats> robotViewStats;

	public RobotViewPanel(Map<Robot, RobotViewStats> robotViewStats) {
		this.robotViewStats = robotViewStats;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
        for (Robot robot : robotViewStats.keySet()) {
        	if(robot.isDead()) {
        		continue;
        	}
        	double normalizedX = (double) robot.getPosition().getX()/Game.MAX_X * this.getSize().width;
        	double normalizedY = (double) robot.getPosition().getY()/Game.MAX_Y * this.getSize().height;
       
        	
        	g.setColor(robotViewStats.get(robot).getColor());
        	g.fillRect((int) normalizedX, (int) normalizedY, 10, 10);
        	
        	
        	normalizedX = (double) robotViewStats.get(robot).getLastHitmark().getX()/Game.MAX_X * this.getSize().width;
        	normalizedY = (double) robotViewStats.get(robot).getLastHitmark().getY()/Game.MAX_Y * this.getSize().height;
        	
        	int lineSize = 5;
        	
        	g.drawLine((int)normalizedX -lineSize, (int)normalizedY -lineSize, (int)normalizedX  + lineSize, (int)normalizedY +lineSize);
        	g.drawLine((int)normalizedX +lineSize, (int)normalizedY -lineSize, (int)normalizedX  - lineSize, (int)normalizedY +lineSize);

        }
    }
	
}
