package com.soapwaster.vcr.behaviour;

import java.io.IOException;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import com.soapwaster.vcr.compiler.VCRLexer;
import com.soapwaster.vcr.compiler.VCRParser;
import com.soapwaster.vcr.robot.Robot;

public class BehaviourFactory {

	private VCRLexer lexer;
    private VCRParser parser;
    private ParseTree tree;
    private String behaviour_filename;
	private Robot robot;
    
    public BehaviourFactory(Robot robot) {
    	this.robot = robot;
    }
    
    public AIBehaviour getCustomBehaviour (String behaviour_filename) throws IOException, BehaviourFactoryException {
    	this.behaviour_filename = behaviour_filename;
    	return getBehaviour(BehaviourEnum.Custom);
    }
  
    
    public AIBehaviour getBehaviour (BehaviourEnum type) throws IOException, BehaviourFactoryException{
        AIBehaviour behaviour = null;
        switch (type){
            case Default:
                behaviour = new DefaultBehaviour(robot);
                break;
            case Shooter:
            	behaviour = new ShooterBehaviour(robot);
            	break;
            case Dummy:
            	behaviour = new DummyBehaviour(robot);
            	break;
            case Custom:
            	if(behaviour_filename == null) {
            		throw new BehaviourFactoryException("Cannot build a custom behaviour if a file to it is not passed. Please call getCustomBehaviour, instead");
            	}
            	behaviour = new CustomBehaviour(robot, behaviour_filename);
            	break;
        }
        CharStream codePointCharStream = CharStreams.fromFileName(behaviour.getAIFilename());
		lexer = new VCRLexer(codePointCharStream);
        parser = new VCRParser(new CommonTokenStream(lexer));
        tree = parser.parse();
        behaviour.setParseTree(tree);
   
        return behaviour;
    }
}
