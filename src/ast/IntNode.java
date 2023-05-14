package ast;

import ast.types.IntType;
import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;

import java.util.ArrayList;

public class IntNode implements Node {
    private int value;

    public IntNode(int value) {
        this.value = value;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable symTable, int nesting) {
        // non ritorna errori semantici, ritorna una lista vuota
        return new ArrayList<SemanticError>();
    }

    @Override
    public Type typeCheck() {
        return new IntType();
    }

    @Override
    public String codeGeneration() {
        // TODO implementare la generazione di codice
        return null;
    }
}
