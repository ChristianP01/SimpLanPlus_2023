package ast;

import parser.SimpLanPlusBaseVisitor;
import parser.SimpLanPlusParser;

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
        return super.visitVarExp(ctx);
    }

    @Override
    public Node visitIntCompExp(SimpLanPlusParser.IntCompExpContext ctx) {
        return super.visitIntCompExp(ctx);
    }

    @Override
    public Node visitIfExp(SimpLanPlusParser.IfExpContext ctx) {
        return super.visitIfExp(ctx);
    }

    @Override
    public Node visitSumSubExp(SimpLanPlusParser.SumSubExpContext ctx) {
        return super.visitSumSubExp(ctx);
    }

    @Override
    public Node visitBoolBinExp(SimpLanPlusParser.BoolBinExpContext ctx) {

    }

    @Override
    public Node visitBoolExp(SimpLanPlusParser.BoolExpContext ctx) {
        return new BoolNode(Boolean.parseBoolean(ctx.getText()));
    }

    @Override
    public Node visitNegExp(SimpLanPlusParser.NegExpContext ctx) {
        // TODO da implementare dopo aver scritto NegNode
        return super.visitNegExp(ctx);
    }

    @Override
    public Node visitMultDivExp(SimpLanPlusParser.MultDivExpContext ctx) {
        return super.visitMultDivExp(ctx);
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
