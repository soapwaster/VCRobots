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

@SuppressWarnings("deprecation")
public class RobotBehaviourVisitor extends VCRBaseVisitor<VCRValue> {

    // used to compare floating point numbers
    public static final double SMALL_VALUE = 0.00000000001;
    
    private Robot robot;
   
    // store variables (there's only one global scope!)
    private Map<String, VCRValue> memory = new HashMap<String, VCRValue>();
    
    public RobotBehaviourVisitor(Robot robot) {
		super();
		this.robot = robot;
	}

	// assignment/id overrides
    @Override
    public VCRValue visitAssignment(VCRParser.AssignmentContext ctx) {
        String id = ctx.ID().getText();
        VCRValue value = this.visit(ctx.expr());
        return memory.put(id, value);
    }

    @Override
    public VCRValue visitIdAtom(VCRParser.IdAtomContext ctx) {
        String id = ctx.getText();
        VCRValue value = memory.get(id);
        if(value == null) {
            throw new RuntimeException("no such variable: " + id);
        }
        return value;
    }

    // atom overrides
    @Override
    public VCRValue visitStringAtom(VCRParser.StringAtomContext ctx) {
        String str = ctx.getText();
        // strip quotes
        str = str.substring(1, str.length() - 1).replace("\"\"", "\"");
        return new VCRValue(str);
    }

    @Override
    public VCRValue visitNumberAtom(VCRParser.NumberAtomContext ctx) {
        return new VCRValue(Integer.valueOf(ctx.getText()));
    }

    @Override
    public VCRValue visitBooleanAtom(VCRParser.BooleanAtomContext ctx) {
        return new VCRValue(Boolean.valueOf(ctx.getText()));
    }

    @Override
    public VCRValue visitNilAtom(VCRParser.NilAtomContext ctx) {
        return new VCRValue(null);
    }

    // expr overrides
    @Override
    public VCRValue visitParExpr(VCRParser.ParExprContext ctx) {
        return this.visit(ctx.expr());
    }

    @Override
    public VCRValue visitUnaryMinusExpr(VCRParser.UnaryMinusExprContext ctx) {
        VCRValue value = this.visit(ctx.expr());
        return new VCRValue(-value.asInteger());
    }

    @Override
    public VCRValue visitNotExpr(VCRParser.NotExprContext ctx) {
        VCRValue value = this.visit(ctx.expr());
        return new VCRValue(!value.asBoolean());
    }

    @Override
    public VCRValue visitMultiplicationExpr(@NotNull VCRParser.MultiplicationExprContext ctx) {

        VCRValue left = this.visit(ctx.expr(0));
        VCRValue right = this.visit(ctx.expr(1));

        switch (ctx.op.getType()) {
            case VCRParser.MULT:
                return new VCRValue(left.asInteger() * right.asInteger());
            case VCRParser.DIV:
                return new VCRValue(left.asInteger() / right.asInteger());
            case VCRParser.MOD:
                return new VCRValue(left.asInteger() % right.asInteger());
            default:
                throw new RuntimeException("unknown operator: " + VCRParser.tokenNames[ctx.op.getType()]);
        }
    }

    @Override
    public VCRValue visitAdditiveExpr(@NotNull VCRParser.AdditiveExprContext ctx) {

        VCRValue left = this.visit(ctx.expr(0));
        VCRValue right = this.visit(ctx.expr(1));

        switch (ctx.op.getType()) {
            case VCRParser.PLUS:
                return left.isInteger() && right.isInteger() ?
                        new VCRValue(left.asInteger() + right.asInteger()) :
                        new VCRValue(left.asString() + right.asString());
            case VCRParser.MINUS:
            	return new VCRValue(left.asInteger() - right.asInteger());
            default:
                throw new RuntimeException("unknown operator: " + VCRParser.tokenNames[ctx.op.getType()]);
        }
    }

    @Override
    public VCRValue visitRelationalExpr(@NotNull VCRParser.RelationalExprContext ctx) {

        VCRValue left = this.visit(ctx.expr(0));
        VCRValue right = this.visit(ctx.expr(1));

        switch (ctx.op.getType()) {
            case VCRParser.LT:
                return new VCRValue(left.asInteger() < right.asInteger());
            case VCRParser.LTEQ:
                return new VCRValue(left.asInteger() <= right.asInteger());
            case VCRParser.GT:
                return new VCRValue(left.asInteger() > right.asInteger());
            case VCRParser.GTEQ:
                return new VCRValue(left.asInteger() >= right.asInteger());
            default:
                throw new RuntimeException("unknown operator: " + VCRParser.tokenNames[ctx.op.getType()]);
        }
    }

    @Override
    public VCRValue visitEqualityExpr(@NotNull VCRParser.EqualityExprContext ctx) {

        VCRValue left = this.visit(ctx.expr(0));
        VCRValue right = this.visit(ctx.expr(1));

        switch (ctx.op.getType()) {
            case VCRParser.EQ:
                return left.isInteger() && right.isInteger() ?
                        new VCRValue(Math.abs(left.asInteger() - right.asInteger()) < SMALL_VALUE) :
                        new VCRValue(left.equals(right));
            case VCRParser.NEQ:
                return left.isInteger() && right.isInteger() ?
                        new VCRValue(Math.abs(left.asInteger() - right.asInteger()) >= SMALL_VALUE) :
                        new VCRValue(!left.equals(right));
            default:
                throw new RuntimeException("unknown operator: " + VCRParser.tokenNames[ctx.op.getType()]);
        }
    }

    @Override
    public VCRValue visitAndExpr(VCRParser.AndExprContext ctx) {
        VCRValue left = this.visit(ctx.expr(0));
        VCRValue right = this.visit(ctx.expr(1));
        return new VCRValue(left.asBoolean() && right.asBoolean());
    }

    @Override
    public VCRValue visitOrExpr(VCRParser.OrExprContext ctx) {
        VCRValue left = this.visit(ctx.expr(0));
        VCRValue right = this.visit(ctx.expr(1));
        return new VCRValue(left.asBoolean() || right.asBoolean());
    }

    // if override
    @Override
    public VCRValue visitIf_stat(VCRParser.If_statContext ctx) {

        List<VCRParser.Condition_blockContext> conditions =  ctx.condition_block();

        boolean evaluatedBlock = false;

        for(VCRParser.Condition_blockContext condition : conditions) {

            VCRValue evaluated = this.visit(condition.expr());

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

        return VCRValue.VOID;
    }

    // while override
    @Override
    public VCRValue visitWhile_stat(VCRParser.While_statContext ctx) {

        VCRValue value = this.visit(ctx.expr());

        while(value.asBoolean()) {

            // evaluate the code block
            this.visit(ctx.stat_block());

            // evaluate the expression
            value = this.visit(ctx.expr());
        }

        return VCRValue.VOID;
    }
    
    @Override
    public VCRValue visitMethodCall(MethodCallContext ctx) {
    	
    	String methodName = ctx.methodName().getText();
    	switch (methodName) {
		case "moveTo":{
			int moveToX = visit(ctx.methodCallArguments().expr(0)).asInteger().intValue();
			int moveToY = visit(ctx.methodCallArguments().expr(1)).asInteger().intValue();
			
			Position2D movePos = MathUtils.computePositionGivenRange(robot.getPosition(), new Position2D(moveToX, moveToY, false), robot.getStat().getRange());
	
			Game.getInstance().getMainEventDispatcher().addEvent(new MoveToEvent(robot, robot, movePos));
			break;
		}
		case "shootAt":{
			int shootAtX = visit(ctx.methodCallArguments().expr(0)).asInteger().intValue();
			int shootAtY = visit(ctx.methodCallArguments().expr(1)).asInteger().intValue();

		    Position2D shootPos = MathUtils.computePositionGivenRange(robot.getPosition(), new Position2D(shootAtX, shootAtY, false), robot.getStat().getRange());
			
			Game.getInstance().getMainEventDispatcher().addEvent(new ShootAtEvent(robot, null, shootPos, robot.getStat().getDamage()));
			break;
		}
		case "inRange":{
			int targetX = visit(ctx.methodCallArguments().expr(0)).asInteger().intValue();
			int targetY = visit(ctx.methodCallArguments().expr(1)).asInteger().intValue();
			
			Position2D inRangePos = new Position2D(targetX,targetY);
			
			VCRValue inRange = new VCRValue(robot.inRange(inRangePos));
			
			return inRange;
		}
		case "closestEnemyX":{
			int closestX = Game.getInstance().getClosestRobotXFrom(robot);
			VCRValue closest = new VCRValue(closestX);
			return closest;
		}
		case "closestEnemyY":{
			int closestY = Game.getInstance().getClosestRobotYFrom(robot);
			VCRValue closest = new VCRValue(closestY);
			return closest;
		}
		case "currentX":{
			int currentX = robot.getPosition().getX();
			VCRValue current = new VCRValue(currentX);
			return current;
		}
		case "currentY":{
			int currentY = robot.getPosition().getY();
			VCRValue current = new VCRValue(currentY);
			return current;
		}
		case "gameX":{
			VCRValue gameX = new VCRValue(Game.WIDTH - 1);
			return gameX;
		}
		case "gameY":{
			VCRValue gameY = new VCRValue(Game.HEIGHT - 1);
			return gameY;
		}
		case "hp":{
			VCRValue hp = new VCRValue((int)robot.getHealth());
			return hp;
		}
		case "range":{
			VCRValue range = new VCRValue(robot.getStat().getRange());
			return range;
		}
		default:
			System.err.println("\n No method '" + methodName + "' exists...exiting the game");
			System.exit(1);
		}
    	return VCRValue.VOID;
    }
}