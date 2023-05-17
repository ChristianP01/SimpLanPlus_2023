package ast;

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
        return exp.typeCheck();
    }

    @Override
    public String codeGeneration() {
        return null;
    }
}
