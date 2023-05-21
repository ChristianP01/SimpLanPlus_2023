package ast;

import ast.declarations.DecFunNode;
import ast.declarations.DecNode;
import ast.expressions.*;
import ast.statements.IfStmNode;
import ast.types.FunType;
import ast.types.Type;
import parser.SimpLanPlusBaseVisitor;
import parser.SimpLanPlusParser;
import java.util.*;
import java.util.stream.Collectors;

public class SLPVisitor extends SimpLanPlusBaseVisitor<Node> {
    @Override
    public Node visitSingleExpProg(SimpLanPlusParser.SingleExpProgContext ctx) {
        Node exp = visit(ctx.exp());
        return new ProgNode(exp);
    }

    @Override
    public Node visitDecStmExpProg(SimpLanPlusParser.DecStmExpProgContext ctx) {
        // visita di ogni dichiarazione, inserimento dei corrispondenti nodi in un ArrayList
        ArrayList<Node> decs = ctx.dec().stream()
                .map(this::visit)
                .collect(Collectors.toCollection(ArrayList::new));

        // visita di ogni statement (se presenti), inserimento dei corrispondenti nodi in un ArrayList
        ArrayList<Node> stms = ctx.stm().stream()
                .map(this::visit)
                .collect(Collectors.toCollection(ArrayList::new));

        Node exp = visit(ctx.exp());

        return new ProgDecNode(decs, stms, exp);
    }

    @Override
    public Node visitVarDec(SimpLanPlusParser.VarDecContext ctx) {
        Type type = (Type) visit(ctx.type());
        String id = ctx.ID().getText();

        return new DecNode(type, id);
    }

    @Override
    public Node visitFunDec(SimpLanPlusParser.FunDecContext ctx) {
        String id = ctx.ID().getText();
        ArrayList<Node> params = new ArrayList<>();

        // tipo di ritorno della funzione
        Type returnType = (Type) visit(ctx.type());

        for (SimpLanPlusParser.ParamContext param : ctx.param()) {
            params.add(visit(param));
        }

        // tipi dei parametri
        ArrayList<Type> paramTypes = ctx.param().stream()
                .map(p -> visit(p.type()).typeCheck())
                .collect(Collectors.toCollection(ArrayList::new));

        // visita delle dichiarazioni
        ArrayList<Node> decs = ctx.body().dec().stream()
                .map(d -> visit(d))
                .collect(Collectors.toCollection(ArrayList::new));

        // visita degli statements
        ArrayList<Node> stms = ctx.body().stm().stream()
                .map(s -> visit(s))
                .collect(Collectors.toCollection(ArrayList::new));

        // visita dell'espressione finale del corpo
        Node exp = visit(ctx.body().exp());

        return new DecFunNode(new FunType(paramTypes, returnType), id, params, decs, stms, exp);
    }

    @Override
    public Node visitParam(SimpLanPlusParser.ParamContext ctx) {
        Node type = visit(ctx.type());
        String id = ctx.ID().getText();

        return new DecNode(type, id);
    }

    @Override
    public Node visitBody(SimpLanPlusParser.BodyContext ctx) {
        return super.visitBody(ctx);
    }

    @Override
    public Node visitIfbody(SimpLanPlusParser.IfbodyContext ctx) {
        // visita degli statements
        ArrayList<Node> stms = ctx.stm().stream()
                .map(this::visit)
                .collect(Collectors.toCollection(ArrayList::new));

        // visita dell'espressione
        Node exp = visit(ctx.exp());

        return new IfBodyNode(stms, exp);
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
        // TODO controllare che il ramo else sia presente
        Node cond = visit(ctx.cond);
        ArrayList<Node> thenStms = ctx.thenBranch.stm().stream()
                .map(s -> visit(s))
                .collect(Collectors.toCollection(ArrayList::new));

        ArrayList<Node> elseStms = ctx.elseBranch.stm().stream()
                .map(s -> visit(s))
                .collect(Collectors.toCollection(ArrayList::new));

        return new IfStmNode(cond, thenStms, elseStms);
    }

    @Override
    public Node visitBaseExp(SimpLanPlusParser.BaseExpContext ctx) {
        return visit(ctx.exp());
    }

    @Override
    public Node visitVarExp(SimpLanPlusParser.VarExpContext ctx) {
        String id = ctx.ID().getText();

        return new IdNode(id);
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
        String id = ctx.ID().getText();
        ArrayList<Node> params = new ArrayList<>();

        for (SimpLanPlusParser.ExpContext exp : ctx.exp()) {
            params.add(visit(exp));
        }
        return new FunCallNode(id, params);
    }

    @Override
    public Node visitIntExp(SimpLanPlusParser.IntExpContext ctx) {
        return new IntNode(Integer.parseInt(ctx.INTEGER().getText()));
    }
}
