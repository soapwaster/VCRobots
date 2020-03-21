package com.soapwaster.vcr.robot_game;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.cli.*;

import com.soapwaster.vcr.robot.CustomRobot;
import com.soapwaster.vcr.robot.DefaultRobot;
import com.soapwaster.vcr.robot.DummyRobot;
import com.soapwaster.vcr.robot.Robot;
import com.soapwaster.vcr.robot.ShooterRobot;

public class RobotBattle 
{
    public static void main( String[] args )
    {
    	int maxPlayers = 4;
    	List<Robot> robots = new ArrayList<>();
    	
    	Options options = new Options();

        Option gameSize = Option.builder("gs")
        				  .argName("width> <height")
        				  .longOpt("game_size")
        				  .required(false)
        				  .desc("game space size (default: 1000 1000)")
        				  .numberOfArgs(2)
        				  .type(Integer.class)
        				  .build();
        
        Option robotPositions = Option.builder("rp")
						  .longOpt("random_positions")
						  .required(false)
						  .desc("robot positions. Either 'true' or 'false' (default: false)")
						  .hasArg(true)
						  .build();
        
        Option powerup = Option.builder("pi")
        				.longOpt("powerup_interval")
        				.argName("interval")
        				.desc("milliseconds after each powerup (default: 300)")
        				.hasArg()
        				.required(false)
        				.type(Integer.class)
        				.build();
        
        Option robot = Option.builder("r")
        			   .argName("name,type,positionX,positionY+")
        			   .desc("robot players (up to "+maxPlayers+") type can be one of the following <Default, Shooter, Dummy>."
        			   		+ " In case of a custom robot, put the filename with the behaviour")
        			   .numberOfArgs(maxPlayers)
        			   .hasArgs()
        			   .required()
        			   .valueSeparator(' ')
        			   .longOpt("robot")
        			   .type(String.class)
        			   .build();
        
        options.addOption(robot);
        options.addOption(robotPositions);
        options.addOption(gameSize);
        options.addOption(powerup);
        
        
        
        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            formatter.printHelp("VCRobots", options);
            System.exit(1);
        }
        
        Integer powerupInterval = Integer.valueOf(cmd.getOptionValue("pi", "300"));
        
        String[] gameSizeValue = cmd.getOptionValues("gs");
        
        Integer gameWidth  = 1000;
        Integer gameHeight = 1000;
        
        if(gameSizeValue != null) {
        	gameWidth  = Integer.valueOf(Optional.ofNullable(gameSizeValue[0]).orElse("1000"));
            gameHeight = Integer.valueOf(Optional.ofNullable(gameSizeValue[1]).orElse("1000"));
        }
        
        //This sets up the dimensions in advance. So that robot positions are not normalized with default values
        Game.WIDTH = gameWidth;
        Game.HEIGHT = gameHeight;
        
        
        Boolean randomPositions = Boolean.valueOf(cmd.getOptionValue("rp"));
         
        String[] robotValues = cmd.getOptionValues("r");
        
        if(robotValues.length < 2) {
        	System.out.println("please input at least two robots");
        	System.exit(1);
        }
       
        for (int i = 0; i<robotValues.length && i < maxPlayers; i++) {
       
        	String[] robotProperties = robotValues[i].split(",");
        	if(robotProperties.length != 4) {
        		System.out.println("please specify 4 parameters for -r <name,type,positionX,positionY>");
        		System.exit(1);
        	}
        	String robotName = robotProperties[0].strip();
        	String robotType = robotProperties[1].strip();
        	Integer robotX = Integer.parseInt(robotProperties[2].strip());
        	Integer robotY = Integer.parseInt(robotProperties[3].strip());
        	
        	Position2D robotPos = new Position2D(robotX, robotY);
        	
        	switch (robotType) {
			case "Default":
				robots.add(new DefaultRobot(robotName,robotPos));
				break;
			case "Dummy":
				robots.add(new DummyRobot(robotName, robotPos));
				break;
			case "Shooter":
				robots.add(new ShooterRobot(robotName, robotPos));
				break;
			default:
				robots.add(new CustomRobot(robotName, robotType, robotPos));
				break;
			}
		}
        
        Game.getInstance().setupGame(robots, randomPositions, gameWidth, gameHeight, powerupInterval);
        Game.getInstance().startGame();
    	
   
    }
}
