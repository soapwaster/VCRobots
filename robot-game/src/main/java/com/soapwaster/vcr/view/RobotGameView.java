package com.soapwaster.vcr.view;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

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
import java.awt.Graphics;

import javax.swing.SwingConstants;

import com.soapwaster.vcr.robot.Robot;
import com.soapwaster.vcr.robot_game.Game;
import com.soapwaster.vcr.robot_game.Position2D;
import javax.swing.border.LineBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import java.awt.Font;
import javax.swing.JTextPane;

/**
 * The main View that shows the Robot battle.
 * Contains :
 * <ul>
 * <li> Stats for each robot (hp, range, damage)
 * <li> Robot battlefield
 * <li> Log section that describes every event happening in the battlefield
 * <li> A winned section that declares the winner at the end of the battle
 * </ul>
 *
 */
public class RobotGameView implements PropertyChangeListener{

	private JFrame frame;
	private List<Robot> robots;
	private Map<Robot, RobotViewStats> robotViewStats;
	private RobotViewPanel robotBattlePanel;
	private JLabel winnerLabel;
	private JTextPane logPane;

	/**
	 * Creates a RobotGame view.
	 */
	public RobotGameView(List<Robot> robots) {
		this.robots = robots;
		robotViewStats = new HashMap<Robot, RobotViewStats>();
		Color robotColors[] = new Color[] {Color.ORANGE, Color.BLUE, Color.RED, Color.DARK_GRAY};
		for (Robot robot : robots) {
			RobotViewStats rvs = new RobotViewStats();
			JLabel hpLabel = new JLabel(String.valueOf(robot.getHealth()));
			JLabel rangeLabel = new JLabel(String.valueOf(robot.getStat().getRange()));
			JLabel damageLabel = new JLabel(String.valueOf(robot.getStat().getDamage()));
			
			rvs.setHpLabel(hpLabel);
			rvs.setDamageLabel(damageLabel);
			rvs.setRangeLabel(rangeLabel);
			
			robotViewStats.put(robot, rvs);
			robotViewStats.get(robot).setColor(robotColors[robotViewStats.size()-1]);
			robot.addPropertyChangeListener(this);
		}
		
		robotBattlePanel = new RobotViewPanel(robotViewStats);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel overallStatsPanel = new JPanel();
		frame.getContentPane().add(overallStatsPanel, BorderLayout.EAST);
		overallStatsPanel.setLayout(new GridLayout(0, 2, 0, 0));
		
		
		for (Robot robot : robots) {
			JPanel statsPanel = new JPanel();
			statsPanel.setBorder(new LineBorder(Color.GRAY));
			overallStatsPanel.add(statsPanel);
			statsPanel.setLayout(new BorderLayout(0, 0));
			
			//Robot name label setup
			JLabel robotNameLabel = new JLabel(robot.toString());
			robotNameLabel.setForeground(robotViewStats.get(robot).getColor());
			robotNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
			statsPanel.add(robotNameLabel, BorderLayout.NORTH);
			
			JPanel gridPanel = new JPanel();
			statsPanel.add(gridPanel, BorderLayout.CENTER);
			gridPanel.setLayout(new GridLayout(3, 2, 0, 0));
			 
			//HP info 
			gridPanel.add(new JLabel("Hp: "));
			gridPanel.add(robotViewStats.get(robot).getHpLabel());
			
			//Range info
			gridPanel.add(new JLabel("Range:"));
			gridPanel.add(robotViewStats.get(robot).getRangeLabel());
			
			//Damage info
			gridPanel.add(new JLabel("Damage:"));
			gridPanel.add(robotViewStats.get(robot).getDamageLabel());
		}
		
		
		frame.setSize(new Dimension(900, 550));
		frame.getContentPane().add(robotBattlePanel, BorderLayout.CENTER);
		
		winnerLabel = new JLabel();
		winnerLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		winnerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(winnerLabel, BorderLayout.NORTH);
		
		logPane = new JTextPane();
		JScrollPane scrollLogPane = new JScrollPane(logPane);
		scrollLogPane.setPreferredSize(new Dimension(frame.getSize().width, 200));
		frame.getContentPane().add(scrollLogPane, BorderLayout.SOUTH);
		
	}

	/**
	 * Makes the battle ground visible
	 */
	public void showBattleGround() {
		frame.setVisible(true);
	}
	
	/**
	 * Updates the game battle view by drawing robots in their positions together with their last hit mark
	 */
	private void redrawView() {
		robotBattlePanel.revalidate();
		robotBattlePanel.repaint();
	}

	/**
	 * Handles a subject property change notification
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String pName = evt.getPropertyName();
		PropertyChangeType pcType = PropertyChangeType.valueOf(pName); 
		switch (pcType) {
		case Hit:{
			for (Robot robot : robots) {
				String health = String.valueOf(robot.getHealth());
				robotViewStats.get(robot).getHpLabel().setText(health);
			}
			break;	
		}
		case Stats:{
			for (Robot robot : robots) {
				String range = String.valueOf(robot.getStat().getRange());
				String damage = String.valueOf(robot.getStat().getDamage());
				robotViewStats.get(robot).getRangeLabel().setText(range);
				robotViewStats.get(robot).getDamageLabel().setText(damage);
			}
			break;
		}
		case Move:{
			redrawView();
			break;	
		}
		case Shoot:{
			Position2D shootPos = (Position2D) evt.getNewValue();
			Robot source = (Robot) evt.getSource();
			
			robotViewStats.get(source).setLastHitmark(shootPos);
			
			redrawView();
			break;	
		}
		case Win:{
			Robot source = (Robot) evt.getSource();
			
			winnerLabel.setText(source + " won the battle with " + (int) source.getHealth() + " HP left!");
			
			redrawView();
			break;	
		}
		case Log:{
			Robot source = (Robot) evt.getSource();
			String logDescription = (String) evt.getNewValue();
			
			appendToPane(logPane, source.toString(), robotViewStats.get(source).getColor());
		    appendToPane(logPane, ": " + logDescription + "\n\n", Color.BLACK);
			
			redrawView();
			break;	
		}

		default:
			break;
		}
		
	}
	
	/**
	 * Appends colored message a JTextPane
	 * @param textPane 
	 * @param msg the message to append
	 * @param color the color of the message
	 */
	private void appendToPane(JTextPane textPane, String msg, Color color)
    {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, color);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        int len = textPane.getDocument().getLength();
        textPane.setCaretPosition(len);
        textPane.setCharacterAttributes(aset, false);
        textPane.replaceSelection(msg);
    }

}

/**
 * The panel that shows in real time the battle
 */
class RobotViewPanel extends JPanel {

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
        	
        	Position2D robotPosition = getScaledPosition(robot.getPosition());
        	g.setColor(robotViewStats.get(robot).getColor());
        	g.fillRect(robotPosition.getX()-5, robotPosition.getY()-5, 10, 10);
        	
        	int lineSize = 4;
        	Position2D hitmarkPosition = getScaledPosition(robotViewStats.get(robot).getLastHitmark());
        	g.setColor(robotViewStats.get(robot).getColor());
        	g.drawLine((int)hitmarkPosition.getX() -lineSize, (int)hitmarkPosition.getY() -lineSize, (int)hitmarkPosition.getX()  + lineSize, (int)hitmarkPosition.getY() +lineSize);
        	g.drawLine((int)hitmarkPosition.getX() +lineSize, (int)hitmarkPosition.getY() -lineSize, (int)hitmarkPosition.getX()  - lineSize, (int)hitmarkPosition.getY() +lineSize);

        }
    }

	/**
	 * Scales the position to accomodate panel size
	 * @param position
	 * @return scaled position
	 */
	private Position2D getScaledPosition(Position2D position) {
		double scaledX = (double) position.getX()/Game.WIDTH * this.getSize().width;
    	double scaledY = (double) position.getY()/Game.HEIGHT * this.getSize().height;
    	
    	return new Position2D((int)scaledX, (int)scaledY, false);
	}
	
}
