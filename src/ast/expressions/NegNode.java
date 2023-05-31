package ast.expressions;

import ast.Node;
import ast.simplanlib.SimplanInterface;
import ast.types.Type;
import ast.types.BoolType;
import ast.types.ErrorType;
import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;

import java.util.ArrayList;

public class NegNode implements Node {
    private Node exp;

    public NegNode(Node exp) {
        this.exp = exp;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable symTable, int nesting) {
        return exp.checkSemantics(symTable, nesting);
    }

    @Override
    public Type typeCheck() {
        if(exp.typeCheck() instanceof BoolType)
            return new BoolType();
        else {
            System.out.println("Type mismatch: negation expects a bool type, got " + this.exp.typeCheck().toString() + " type instead.");
            return new ErrorType();
        }
    }

    @Override
    public String codeGeneration() {
        String exitLabel = SimplanInterface.newLabel();
        String trueLabel = SimplanInterface.newLabel();

        return exp.codeGeneration() +
                "storei T1 1 \n" +
                "beq A0 T1 " + trueLabel + "\n" +
                "storei A0 1 \n" +
                "b" + exitLabel + "\n" +
                trueLabel + ": \n" +
                "storei A0 0 \n" +
                exitLabel + ": \n";
    }

    @Override
    public String toPrint(String s) {
        return s + "Negation\n" + this.exp.toPrint(s + "\t");
    }
}
