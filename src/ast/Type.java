package ast;

import semanticanalysis.STEntry;
import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;

import java.util.ArrayList;

public class Type implements Node {
    public boolean isEqual(Type A, Type B) {
        return A.getClass().equals(B.getClass());
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable st, int nesting) {
        return null;
    }

    @Override
    public Type typeCheck() {
        return null;
    }

    @Override
    public String codeGeneration() {
        return "";
    }
}
