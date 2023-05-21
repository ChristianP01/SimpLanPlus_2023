package ast.expressions;

import ast.Node;
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
        if(exp.typeCheck() instanceof BoolType) return new BoolType();

        System.out.println("Type mismatch: negation expects a bool type, got " + this.exp.typeCheck().toString() + " type instead.");
        return exp.typeCheck() instanceof BoolType ? new BoolType() : new ErrorType();
    }

    @Override
    public String codeGeneration() {
        return null;
    }
}
