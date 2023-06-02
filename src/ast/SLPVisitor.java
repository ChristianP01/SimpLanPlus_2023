package ast;

import ast.declarations.DecFunNode;
import ast.declarations.DecNode;
import ast.expressions.*;
import ast.statements.AssgnNode;
import ast.statements.FunCallStmNode;
import ast.statements.IfStmNode;
import ast.types.*;
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

        Node exp = null;
        if(ctx.exp() != null) {
            exp = visit(ctx.exp());
        }

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

        // Visita dei parametri formali della funzione
        if(ctx.firstParam != null) {
            params.add(visit(ctx.firstParam));
        }
        if(ctx.otherParams != null) {
            params.add(visit(ctx.otherParams));
        }

        // Recupero dei tipi dei parametri formali
        ArrayList<Type> paramTypes = params.stream()
                .map(Node::typeCheck)
                .collect(Collectors.toCollection(ArrayList::new));

        // Visita delle dichiarazioni
        ArrayList<Node> decs = ctx.body().dec().stream()
                .map(this::visit)
                .collect(Collectors.toCollection(ArrayList::new));

        // Visita degli statements
        ArrayList<Node> stms = ctx.body().stm().stream()
                .map(this::visit)
                .collect(Collectors.toCollection(ArrayList::new));

        // Visita dell'espressione finale del corpo
        Node exp = null;
        if(ctx.body().exp() != null)
            exp = visit(ctx.body().exp());


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
        // non utilizzato
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
        // non utilizzato
        return super.visitStmifbody(ctx);
    }

    @Override
    public Node visitType(SimpLanPlusParser.TypeContext ctx) {
        String typeName = ctx.getText();
        Node type = switch (typeName) {
            case "int" -> new IntType();
            case "bool" -> new BoolType();
            case "void" -> new VoidType();
            default -> null;
        };

        return type;
    }

    @Override
    public Node visitAssignStm(SimpLanPlusParser.AssignStmContext ctx) {
        String id = ctx.ID().getText();
        Node exp = visit(ctx.exp());
        return new AssgnNode(id, exp);
    }

    @Override
    public Node visitFunCallStm(SimpLanPlusParser.FunCallStmContext ctx) {
        String id = ctx.ID().getText();
        ArrayList<Node> params = new ArrayList<>();

        for (SimpLanPlusParser.ExpContext exp : ctx.exp()) {
            params.add(visit(exp));
        }

        return new FunCallStmNode(id, params);
    }

    @Override
    public Node visitIfStm(SimpLanPlusParser.IfStmContext ctx) {
        Node cond = visit(ctx.cond);
        ArrayList<Node> thenStms = ctx.thenBranch.stm().stream()
                .map(this::visit)
                .collect(Collectors.toCollection(ArrayList::new));
        ArrayList<Node> elseStms = new ArrayList<>();
        if(ctx.elseBranch != null) {
            elseStms = ctx.elseBranch.stm().stream()
                    .map(this::visit)
                    .collect(Collectors.toCollection(ArrayList::new));
        }

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
