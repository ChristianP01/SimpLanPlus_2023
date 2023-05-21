package ast.expressions;

import ast.Node;
import ast.types.Type;
import ast.types.BoolType;
import ast.types.ErrorType;
import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;

import java.util.ArrayList;

public class IfNode implements Node {
    private Node condition;
    private Node thenBranch;
    private Node elseBranch;

    public IfNode(Node condition, Node thenBranch, Node elseBranch) {
        this.condition = condition;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable symTable, int nesting) {
        ArrayList<SemanticError> errors = new ArrayList<SemanticError>();

        errors.addAll(this.condition.checkSemantics(symTable, nesting));
        errors.addAll(this.thenBranch.checkSemantics(symTable, nesting));
        errors.addAll(this.elseBranch.checkSemantics(symTable, nesting));

        return errors;
    }

    @Override
    public Type typeCheck() {
        Type condType = this.condition.typeCheck();
        Type thenType = this.thenBranch.typeCheck();
        Type elseType = this.elseBranch.typeCheck();

        // controllo che la condizione sia un bool
        if(!(condType instanceof BoolType)) {
            System.out.println("If condition must be a bool, got a " + condType.toString() + " instead.");
            return new ErrorType();
        }

        // controllo che i due rami siano dello stesso tipo
        if(!thenType.isEqual(elseType)) {
            System.out.println("In if statement, both branches should have the same type, got " +
                    thenType.toString() + " and " +
                    elseType.toString() + "instead.");
            return new ErrorType();
        }

        return thenType;
    }

    @Override
    public String codeGeneration() {
        return null;
    }

    @Override
    public String toPrint(String s) {
        return s + "If\n" +
                this.condition.toPrint(s + "\t") +
                s + "\t" + "Then branch:\n" +
                this.thenBranch.toPrint(s + "\t") +
                s + "\t" + "Else branch:\n" +
                this.elseBranch.toPrint(s + "\t");
    }
}
