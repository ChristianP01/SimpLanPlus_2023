package ast.types;

import ast.Node;
import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;

import java.util.ArrayList;

public abstract class Type implements Node {

    // metodo statico per l'equivalenza tra tipi
    public boolean isEqual(Type A) {
        return this.getClass().equals(A.getClass());
    }

    public abstract String toString();

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable symTable, int nesting) {
        return null;
    }

    @Override
    public Type typeCheck() {
        return null;
    }

    @Override
    public String codeGeneration() {
        return null;
    }

    @Override
    public String toPrint(String s) {
        return "";
    }
}
