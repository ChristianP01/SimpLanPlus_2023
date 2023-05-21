package ast.statements;

import ast.Node;
import ast.types.Type;
import ast.types.BoolType;
import ast.types.ErrorType;
import ast.types.VoidType;
import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class IfStmNode implements Node {
    private Node condition;
    private ArrayList<Node> thenBranch;
    private ArrayList<Node> elseBranch; // può anche essere vuoto

    public IfStmNode(Node condition, ArrayList<Node> thenBranch, ArrayList<Node> elseBranch) {
        this.condition = condition;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }


    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable symTable, int nesting) {
        ArrayList<SemanticError> errors = new ArrayList<SemanticError>();

        errors.addAll(this.condition.checkSemantics(symTable, nesting));

        for(Node n : thenBranch) {
            errors.addAll(n.checkSemantics(symTable, nesting));
        }

        for(Node n : elseBranch) {
            errors.addAll(n.checkSemantics(symTable, nesting));
        }

        return errors;
    }

    @Override
    public Type typeCheck() {
        Type condType = this.condition.typeCheck();
        if(!(condType instanceof BoolType)) {
            System.out.println("If condition must be a bool, got a " + condType.toString() + " instead.");
            return new ErrorType();
        }

        for(Node n : this.thenBranch) {
            if(!(n.typeCheck() instanceof VoidType)) {
                System.out.print("Then-branch of if statements must be made of statements only, found a "
                        + n.typeCheck().toString() + " instead.");
                return new ErrorType();
            }
        }

        for(Node n : this.elseBranch) {
            if (!(n.typeCheck() instanceof VoidType)) {
                System.out.print("Else-branch of if statements must be made of statements only, found a "
                        + n.typeCheck().toString() + " instead.");
                return new ErrorType();
            }
        }

        return new VoidType();
    }

    @Override
    public String codeGeneration() {
        return null;
    }

    @Override
    public String toPrint(String s) {
        return s + "If statement\n" +
                this.condition.toPrint(s + "\t") +
                this.thenBranch.stream().map(st -> toPrint(st + "\t")).collect(Collectors.joining("\n")) +
                this.elseBranch.stream().map(st -> toPrint(st + "\t")).collect(Collectors.joining("\n"));
    }
}
