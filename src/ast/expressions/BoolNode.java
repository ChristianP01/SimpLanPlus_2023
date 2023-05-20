package ast.expressions;

import ast.Node;
import ast.Type;
import ast.types.BoolType;
import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;

import java.util.ArrayList;

public class BoolNode implements Node {
    private boolean value;

    public BoolNode(boolean value) {
        this.value = value;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable symTable, int nesting) {
        // non ritorna errori semantici, ritorna una lista vuota
        return new ArrayList<SemanticError>();
    }

    @Override
    public Type typeCheck() {
        return new BoolType();
    }

    @Override
    public String codeGeneration() {
        // TODO implementare la generazione di codice
        return null;
    }
}