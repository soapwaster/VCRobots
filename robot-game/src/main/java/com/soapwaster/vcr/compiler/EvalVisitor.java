package com.soapwaster.vcr.compiler;

import org.antlr.v4.runtime.misc.NotNull;

import com.soapwaster.vcr.compiler.VCRParser.MethodCallContext;
import com.soapwaster.vcr.event_handling.MoveToEvent;
import com.soapwaster.vcr.event_handling.ShootAtEvent;
import com.soapwaster.vcr.robot.Robot;
import com.soapwaster.vcr.robot_game.Game;
import com.soapwaster.vcr.robot_game.Position2D;
import com.soapwaster.vcr.utils.MathUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EvalVisitor extends VCRBaseVisitor<Value> {

    // used to compare floating point numbers
    public static final double SMALL_VALUE = 0.00000000001;
    
    private Robot robot;
   
    // store variables (there's only one global scope!)
    private Map<String, Value> memory = new HashMap<String, Value>();
    
    public EvalVisitor(Robot robot) {
		super();
		this.robot = robot;
	}

	// assignment/id overrides
    @Override
    public Value visitAssignment(VCRParser.AssignmentContext ctx) {
        String id = ctx.ID().getText();
        Value value = this.visit(ctx.expr());
        return memory.put(id, value);
    }

    @Override
    public Value visitIdAtom(VCRParser.IdAtomContext ctx) {
        String id = ctx.getText();
        Value value = memory.get(id);
        if(value == null) {
            throw new RuntimeException("no such variable: " + id);
        }
        return value;
    }

    // atom overrides
    @Override
    public Value visitStringAtom(VCRParser.StringAtomContext ctx) {
        String str = ctx.getText();
        // strip quotes
        str = str.substring(1, str.length() - 1).replace("\"\"", "\"");
        return new Value(str);
    }

    @Override
    public Value visitNumberAtom(VCRParser.NumberAtomContext ctx) {
        return new Value(Integer.valueOf(ctx.getText()));
    }

    @Override
    public Value visitBooleanAtom(VCRParser.BooleanAtomContext ctx) {
        return new Value(Boolean.valueOf(ctx.getText()));
    }

    @Override
    public Value visitNilAtom(VCRParser.NilAtomContext ctx) {
        return new Value(null);
    }

    // expr overrides
    @Override
    public Value visitParExpr(VCRParser.ParExprContext ctx) {
        return this.visit(ctx.expr());
    }

    @Override
    public Value visitUnaryMinusExpr(VCRParser.UnaryMinusExprContext ctx) {
        Value value = this.visit(ctx.expr());
        return new Value(-value.asDouble());
    }

    @Override
    public Value visitNotExpr(VCRParser.NotExprContext ctx) {
        Value value = this.visit(ctx.expr());
        return new Value(!value.asBoolean());
    }

    @Override
    public Value visitMultiplicationExpr(@NotNull VCRParser.MultiplicationExprContext ctx) {

        Value left = this.visit(ctx.expr(0));
        Value right = this.visit(ctx.expr(1));

        switch (ctx.op.getType()) {
            case VCRParser.MULT:
                return new Value(left.asDouble() * right.asDouble());
            case VCRParser.DIV:
                return new Value(left.asDouble() / right.asDouble());
            case VCRParser.MOD:
                return new Value(left.asDouble() % right.asDouble());
            default:
                throw new RuntimeException("unknown operator: " + VCRParser.tokenNames[ctx.op.getType()]);
        }
    }

    @Override
    public Value visitAdditiveExpr(@NotNull VCRParser.AdditiveExprContext ctx) {

        Value left = this.visit(ctx.expr(0));
        Value right = this.visit(ctx.expr(1));

        switch (ctx.op.getType()) {
            case VCRParser.PLUS:
                return left.isDouble() && right.isDouble() ?
                        new Value(left.asDouble() + right.asDouble()) :
                        new Value(left.asString() + right.asString());
            case VCRParser.MINUS:
            	return new Value(left.asDouble() - right.asDouble());
            default:
                throw new RuntimeException("unknown operator: " + VCRParser.tokenNames[ctx.op.getType()]);
        }
    }

    @Override
    public Value visitRelationalExpr(@NotNull VCRParser.RelationalExprContext ctx) {

        Value left = this.visit(ctx.expr(0));
        Value right = this.visit(ctx.expr(1));

        switch (ctx.op.getType()) {
            case VCRParser.LT:
                return new Value(left.asDouble() < right.asDouble());
            case VCRParser.LTEQ:
                return new Value(left.asDouble() <= right.asDouble());
            case VCRParser.GT:
                return new Value(left.asDouble() > right.asDouble());
            case VCRParser.GTEQ:
                return new Value(left.asDouble() >= right.asDouble());
            default:
                throw new RuntimeException("unknown operator: " + VCRParser.tokenNames[ctx.op.getType()]);
        }
    }

    @Override
    public Value visitEqualityExpr(@NotNull VCRParser.EqualityExprContext ctx) {

        Value left = this.visit(ctx.expr(0));
        Value right = this.visit(ctx.expr(1));

        switch (ctx.op.getType()) {
            case VCRParser.EQ:
                return left.isDouble() && right.isDouble() ?
                        new Value(Math.abs(left.asDouble() - right.asDouble()) < SMALL_VALUE) :
                        new Value(left.equals(right));
            case VCRParser.NEQ:
                return left.isDouble() && right.isDouble() ?
                        new Value(Math.abs(left.asDouble() - right.asDouble()) >= SMALL_VALUE) :
                        new Value(!left.equals(right));
            default:
                throw new RuntimeException("unknown operator: " + VCRParser.tokenNames[ctx.op.getType()]);
        }
    }

    @Override
    public Value visitAndExpr(VCRParser.AndExprContext ctx) {
        Value left = this.visit(ctx.expr(0));
        Value right = this.visit(ctx.expr(1));
        return new Value(left.asBoolean() && right.asBoolean());
    }

    @Override
    public Value visitOrExpr(VCRParser.OrExprContext ctx) {
        Value left = this.visit(ctx.expr(0));
        Value right = this.visit(ctx.expr(1));
        return new Value(left.asBoolean() || right.asBoolean());
    }

    // log override
    @Override
    public Value visitLog(VCRParser.LogContext ctx) {
        Value value = this.visit(ctx.expr());
        System.out.println(value);
        return value;
    }

    // if override
    @Override
    public Value visitIf_stat(VCRParser.If_statContext ctx) {

        List<VCRParser.Condition_blockContext> conditions =  ctx.condition_block();

        boolean evaluatedBlock = false;

        for(VCRParser.Condition_blockContext condition : conditions) {

            Value evaluated = this.visit(condition.expr());

            if(evaluated.asBoolean()) {
                evaluatedBlock = true;
                // evaluate this block whose expr==true
                this.visit(condition.stat_block());
                break;
            }
        }

        if(!evaluatedBlock && ctx.stat_block() != null) {
            // evaluate the else-stat_block (if present == not null)
            this.visit(ctx.stat_block());
        }

        return Value.VOID;
    }

    // while override
    @Override
    public Value visitWhile_stat(VCRParser.While_statContext ctx) {

        Value value = this.visit(ctx.expr());

        while(value.asBoolean()) {

            // evaluate the code block
            this.visit(ctx.stat_block());

            // evaluate the expression
            value = this.visit(ctx.expr());
        }

        return Value.VOID;
    }
    
    @Override
    public Value visitMethodCall(MethodCallContext ctx) {
    	
    	String methodName = ctx.methodName().getText();
    	switch (methodName) {
		case "moveTo":{
			int moveToX = visit(ctx.methodCallArguments().expr(0)).asInt().intValue();
			int moveToY = visit(ctx.methodCallArguments().expr(1)).asInt().intValue();
			
			Position2D movePos = MathUtils.computePositionGivenRange(robot.getPosition(), new Position2D(moveToX, moveToY), robot.getStat().getRange());
	
			Game.getInstance().getMainEventDispatcher().addEvent(new MoveToEvent(robot, robot, movePos));
			break;
		}
		case "shootAt":{
			int shootAtX = visit(ctx.methodCallArguments().expr(0)).asInt().intValue();
			int shootAtY = visit(ctx.methodCallArguments().expr(1)).asInt().intValue();
			
			Position2D shootPos = MathUtils.computePositionGivenRange(robot.getPosition(), new Position2D(shootAtX, shootAtY), robot.getStat().getRange());
			
			Game.getInstance().getMainEventDispatcher().addEvent(new ShootAtEvent(robot, null, shootPos, robot.getStat().getDamage()));
			break;
		}
		case "inRange":{
			int targetX = visit(ctx.methodCallArguments().expr(0)).asInt().intValue();
			int targetY = visit(ctx.methodCallArguments().expr(1)).asInt().intValue();
			
			Position2D inRangePos = new Position2D(targetX,targetY);
			
			Value inRange = new Value(robot.inRange(inRangePos));
			
//			System.out.println(robot + " " + robot.getPosition() + " : is " + inRangePos + " in my range ? " + inRange);
			return inRange;
		}
		case "closestEnemyX":{
			int closestX = Game.getInstance().getClosestRobotXFrom(robot);
			Value closest = new Value(closestX);
			return closest;
		}
		case "closestEnemyY":{
			int closestY = Game.getInstance().getClosestRobotYFrom(robot);
			Value closest = new Value(closestY);
			return closest;
		}
		case "currentX":{
			int currentX = robot.getPosition().getX();
			Value current = new Value(currentX);
			return current;
		}
		case "currentY":{
			int currentY = robot.getPosition().getY();
			Value current = new Value(currentY);
			return current;
		}
		default:
			break;
		}
    	
    	return Value.VOID;
    }
}