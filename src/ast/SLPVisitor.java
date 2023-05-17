package ast;

import ast.types.BoolType;
import ast.types.IntType;
import parser.SimpLanPlusBaseVisitor;
import parser.SimpLanPlusParser;

import java.util.Objects;

public class SLPVisitor extends SimpLanPlusBaseVisitor<Node> {
    @Override
    public Node visitSingleExpProg(SimpLanPlusParser.SingleExpProgContext ctx) {
        return super.visitSingleExpProg(ctx);
    }

    @Override
    public Node visitDecStmExpProg(SimpLanPlusParser.DecStmExpProgContext ctx) {
        return super.visitDecStmExpProg(ctx);
    }

    @Override
    public Node visitVarDec(SimpLanPlusParser.VarDecContext ctx) {
        return super.visitVarDec(ctx);
    }

    @Override
    public Node visitFunDec(SimpLanPlusParser.FunDecContext ctx) {
        return super.visitFunDec(ctx);
    }

    @Override
    public Node visitParam(SimpLanPlusParser.ParamContext ctx) {
        return super.visitParam(ctx);
    }

    @Override
    public Node visitBody(SimpLanPlusParser.BodyContext ctx) {
        return super.visitBody(ctx);
    }

    @Override
    public Node visitIfbody(SimpLanPlusParser.IfbodyContext ctx) {
        return super.visitIfbody(ctx);
    }

    @Override
    public Node visitStmifbody(SimpLanPlusParser.StmifbodyContext ctx) {
        return super.visitStmifbody(ctx);
    }

    @Override
    public Node visitType(SimpLanPlusParser.TypeContext ctx) {
        return super.visitType(ctx);
    }

    @Override
    public Node visitAssignStm(SimpLanPlusParser.AssignStmContext ctx) {
        return super.visitAssignStm(ctx);
    }

    @Override
    public Node visitFunCallStm(SimpLanPlusParser.FunCallStmContext ctx) {
        return super.visitFunCallStm(ctx);
    }

    @Override
    public Node visitIfStm(SimpLanPlusParser.IfStmContext ctx) {
        return super.visitIfStm(ctx);
    }

    @Override
    public Node visitBaseExp(SimpLanPlusParser.BaseExpContext ctx) {
        return super.visitBaseExp(ctx);
    }

    @Override
    public Node visitVarExp(SimpLanPlusParser.VarExpContext ctx) {
        return new IdNode(ctx.ID().getText());
    }

    @Override
    public Node visitIntCompExp(SimpLanPlusParser.IntCompExpContext ctx) {

        Node left = visit(ctx.left);
        Node right = visit(ctx.right);

        switch (ctx.op.getText()) {
            case ">" -> {
                return new GreaterNode(left, right);
            }

            case "<" -> {
                return new LessNode(left, right);
            }

            case ">=" -> {
                return new GreaterEqualNode(left, right);
            }

            case "<=" -> {
                return new LessEqualNode(left, right);
            }

            case "==" -> {
                return new EqualNode(left, right);
            }

            default -> {
                return null;
            }
        }
    }

    @Override
    public Node visitIfExp(SimpLanPlusParser.IfExpContext ctx) {
        Node condition = visit(ctx.cond);
        Node thenBranch = visit(ctx.thenBranch);
        Node elseBranch = visit(ctx.elseBranch);

        return new IfNode(condition, thenBranch, elseBranch);
    }

    @Override
    public Node visitSumSubExp(SimpLanPlusParser.SumSubExpContext ctx) {
        Node left = visit(ctx.left);
        Node right = visit(ctx.right);

        return Objects.equals(ctx.op.getText(), "+") ? new SumNode(left, right) : new SubNode(left, right);
    }

    @Override
    public Node visitBoolBinExp(SimpLanPlusParser.BoolBinExpContext ctx) {
        Node left = visit(ctx.left);
        Node right = visit(ctx.right);
        return Objects.equals(ctx.op.getText(), "&&") ? new AndNode(left, right) : new OrNode(left, right);
    }

    @Override
    public Node visitBoolExp(SimpLanPlusParser.BoolExpContext ctx) {
        return new BoolNode(Boolean.parseBoolean(ctx.getText()));
    }

    @Override
    public Node visitNegExp(SimpLanPlusParser.NegExpContext ctx) {
        Node exp = visit(ctx.exp());
        return new NegNode(exp);
    }

    @Override
    public Node visitMultDivExp(SimpLanPlusParser.MultDivExpContext ctx) {
        Node left = visit(ctx.left);
        Node right = visit(ctx.right);
        return Objects.equals(ctx.op.getText(), "*") ? new MultNode(left, right) : new DivNode(left, right);
    }

    @Override
    public Node visitFunExp(SimpLanPlusParser.FunExpContext ctx) {
        return super.visitFunExp(ctx);
    }

    @Override
    public Node visitIntExp(SimpLanPlusParser.IntExpContext ctx) {
        return new IntNode(Integer.parseInt(ctx.INTEGER().getText()));
    }
}
