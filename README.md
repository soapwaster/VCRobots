# VCRobots
VCRobots is a *programming* robot battle simulators. That means that players commanding the robot can decide what the robot should do at each turn of the gameplay. For example, you may program your robot to move as close as possible to the target and start shooting. Otherwise, you may decide to move the robot to one of the sides of the arena and adopt a more conservative strategy. The only limit is your fantasy...and the language you write your robot's behavior.
 

## Game architecture 
Each robot (from 2 to 4) has an AI behavior that is executed independently by its own thread. The behavior can be one of the pre-defined one (*Shooter*, *Default*, *Dummy*) or created by writing it in a .vcr file. The game takes the .vcr file containing the behavior, parses it into an AST (Abstract Syntax Tree) that is then visited by the **Visitor Pattern** and executed. </br>
To enforce decoupling, communication such as *movement*, *shooting* or *death* notification passes via an **Event Bus**, a single, asynchronous, blocking priority deque (for concurrency, since many threads access it concurrently) that takes every event (containing sender, receiver and payload) and dispatches it to the receiver if specified, otherwise to every listener, which in this case are robots, but could be any type of entity. Thanks to the Visitor Pattern, FIFO ordering w.r.t. a single robot is maintained (except with "*Dead*" events which are high priority events and are executed as soon as possible). Concurrent events coming from different sources will be inserted according to the O.S. scheduler, that assures fairness in event dispatching. The event bus is also used to dispatch power-ups troughout the game, in order to make it more random.</br>
The robot battle taking place can be visualized via a view written using the Java Swing library. At any time, it shows for each robot : *HP, Range, Damage* current values, together with its current position and last shot position. Robots do not know of the existence of this view, they just notify their **Observers** (in this case the view) trough the **Oberver Pattern**. The view also shows a log of what has happened in the battle. The view can be resized without altering robots relative game positions.</br>
Only one battle at the time can take place since the **Game** entity is a **Singleton**.

 ## Compilation instructions 
The game comes in a Maven project with the following dependencies :

 - ANTLR4 - Domain Specific Language definition and parsing
 - JUnit - testing
 - Apache CLI - command line arguments parsing
 
To compile the project, you should have **Java 11** installed.
Open a terminal and move in the main directory containing the pom.xml file and run:

		mvn install clean
		mvn package

this will install any missing dependency and compile the game into a .jar file named "VCRobots-0.1-jar-with-dependencies.jar" in the *target* folder. </br>
To run it, run this command from the main directory:

		java -jar target/VCRobots-0.1-jar-with-dependencies.jar
This will show an help message that guides you towards how to customize your game. The parameters 

- -gs \<**width**\> \<**height**\> game space size (default: 1000 1000)
- -pi \<**interval**\> milliseconds after each powerup (default: 300)
- -rp \<**arg**\>  robots initial positions. Either 'true' or 'false' (default: false)
- -r \<**name,type,positionX,positionY**+> robot players (from 2, up to 4) [**required**]
	* **type** can be one of the following <Default, Shooter, Dummy>. In case of a custom robot, put the path to the behavior.
---
As an example we want to run a game between 3 players, all with the default behavior, starting in random positions in a 2000 x 3000 game space. The command would be:

		java -jar target/VCRobots-0.1-jar-with-dependencies.jar -r Valerio,Default,300,300 Nino,Default,900,900 Pino,Default,500,400 -gs 2000 3000 -rp true

## Game rules
It is time to know how the game works.</br>
### Game mechanics
The game features a battle simulation between 2 to 4 robots. The robots start in various positions of the game arena and behave accordingly to their AI. There exist three type of pre-definided robot AIs:
* **Default** : moves and shoot
* **Shooter**: moves towards the closest target and shoots around four times
* **Dummy**: doesn't do anything, useful for testing new AIs

However new AIs can be defined as input to the game.

Every robot has three main attributes:
- HP : health points (when reaches 0 the robot dies)
- Range : amount of game units the robot can move or shoot with a step
- Damage : amount of health removed a robot is it with a shot

At every interval of time a power-up is delivered to one of the alive robots. Every power-up has its own unique features. There are three types:

- AimEnchanter : increases Range
- BubbleBlast : increases Damage
- MissledDamage : increases both Range and Damage

Only one player at the time can win.
### Arena layout
The game arena is a X by Y game unit rectangle and robots can move freely from the position [0,0] to [X-1, Y-1]. Whenever a robot surpasses arena bounds, it is teleported to the opposite side of the arena.

### Robot capabilities
A robot in its behavior .vcr file can do many things. It can:

- variable assignments
- compute integer arithmetical expressions ( + - * / % )
- compute comparison expressions ( && || ! == != < > <= >= )
- insert conditional statements (if/else)
- have while loops
- call game-specific methods:
	
	- **gameX()** [int] :- returns game space WIDTH-1 (so that it doesn't overflow)
	- **gameY()** [int] :- returns game space HEIGHT-1 (so that it doesn't overflow)
	- **currentX()** [int] :- retrieves robot current X position in the game space
	- **currentY()** [int] :- retrieves robot current Y position in the game space
	- **range()** [int] :- returns robot's range
	- **hp()** [int] :- returns robot's health
	- **closestEnemyX()** [int] :- retrieves robot's nearest enemy current X position 
	- **closestEnemyY()** [int] :- retrieves robot's nearest enemy current Y position 
	- **moveTo(X,Y)** [void] :- tries to move robot to [X,Y]. If it is not reachable because of its current range, then the robot is moved to the farthest position according to the range
	- **shootAt(X,Y)** [void] :- tries to shoot at [X,Y]. If it is not reachable because of its current range, then the robot shoots to the farthest position according to the range. If the shot hits an enemy, that enemy's health is decreased by an amount equal to the damage attribute
	- **inRange(X,Y)** [bool] :- returns whether the position [X,Y] is reachable within robots range

Whenever the robot moves, shoot or checks whether a position is in its range, it does it by moving towards the shorterst path within the game space.
### How to write an AI
Any AI has to follow a grammar defined in: 		

	src/main/antlr4/com/soapwaster/vcr/compiler/VCR.g4
However, this here's a guideline on how to do each of the capabilities defined beforehand.

 - variable assignment
	
		y = 5 * currentX()
		x = -10 + y
		w = 5 > 4
- conditional statement 
		
	   if( boolean expression ) {
	    ...
	   }
	   else{
	    ...
	   }
- while loop

	  while( boolean expression ){
	   ...
	  }
- game-specific methods
	
	  methodName(expression, expression)
	  shootAt(closestX(), x * 5)
	  moveTo(x,y)
	  inRange(20,100)

For further information, look at VCR.g4.
Now let's look at an AI example. We want to examine the Default Robot behavior defined in "*src/main/resources/robot_ai/default.vcr*".

	myX = currentX()
	myY = currentY()
	x = closestEnemyX()
	y = closestEnemyY()
	
	if(inRange(x,y)){
		shootAt(x,y)
	}
	else{
		i = 10
		while(i >= 5){
			moveTo(x+30,y+30)
			i = i - 1
			}
	}
It tries to find whether the closes enemy is in range. If it is, then it shoots to it. Otherwise it moves 6 times diagonally towards the enemy, making 42 game unit steps at the time.

## Improvements and Tradeoffs
Some things have been left partly "undone" on purpose to show how the game could be extended, and with how much effort.

To add a new attribute to the robot, in order to distinguish movement range from shooting range and have power-ups that increase the newly defined range. To do so, we have to add the attribute and new accessors to the RobotStats.java class , add new **Decorators** if we want new power-ups. The Power up dispatcher (StatsIncreaser.java) does not know anything about the instantiation of new decorators. Those are handled in the StatsFactory class.

To have an unbounded number of players, we could do it by adding a Color generator. This is the only thing to do.

To improve fairness and have an event queue for each robot running independently its own thread, we should modify the EventDispatcher and the EventDispatcherExecutor classes. Producers and Consumers only know that they have to add and retrieve Events, they do not care about the implementation. This could be done to avoid possible starvation in case of infinite loops in a behavior.

To add a new type of robot, we have to introduce a new class that inherits from the Robot class. No other class knows about its implementation. They use the Robot abstract class.

The same goes for the behaviors. We have to add a file in "*_src/main/resources/robot_ai/_*" and add a new element in the BehaviourFactory class. 

Every event is executed by RobotExecutor. It makes it so that event handling is decoupled from the robot class itself, but I decided to use a switch chase instead of polymorphism, to avoid coupling Events to Robot, in case we may want to add new entities as listeners, such as blocking walls that have to be shot to be destroyed.

Lastly, by making communication pass via an Event Bus, transitioning towards a client server architecture would not require a complete refactor, since we may introduce via an **Adapter Pattern** compliance with a 3rd party library that supports Publisher/Subscriber via the Web without changing how threads intract with the EventDispatcher class.

## Future Improvements with unlimited time

 1. Improve error handling. As of now, only basic errors are handled
 2. Security is taken into account, for example no Java methods can be directly called via the code, it would be helpful not to show syntax errors when writing an AI
 3. Extend the grammar
 4. Possibility to update the AI behavior file in real time, so that you can debug the behavior without having to re-compile the project. This would take some effort, but would be a nice feature to adapt to enemies
 5. Client / Server architecture 
 6. Introduction of States for Robots
 7. An AI that improves thanks to reinforcement learning

