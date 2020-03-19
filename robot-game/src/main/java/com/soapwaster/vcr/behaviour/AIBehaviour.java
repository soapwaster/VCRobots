package com.soapwaster.vcr.behaviour;

import org.antlr.v4.runtime.tree.ParseTree;

import com.soapwaster.vcr.compiler.EvalVisitor;
import com.soapwaster.vcr.robot.Robot;

public abstract class AIBehaviour {
	
	EvalVisitor visitor;
	ParseTree tree = null;
	String filename = null;
	Robot robot;

	public abstract String getType();
	
	public AIBehaviour(Robot robot) {
		super();
		this.robot = robot;
		this.visitor = new EvalVisitor(robot);
	}

	public String getAIFilename() {
		return filename;
	}
	public void setParseTree(ParseTree tree) {
		this.tree = tree;
	}
	public void startTurn() {
		visitor.visit(tree);
	}

}
