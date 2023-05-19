package ast.expressions;

import ast.Node;
import ast.Type;
import ast.types.ErrorType;
import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;

import java.util.ArrayList;

public class IdNode implements Node {

    private String id;

    public IdNode(String id) {
        this.id = id;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable symTable, int nesting) {
        ArrayList<SemanticError> errors = new ArrayList<SemanticError>();
        if(symTable.lookup(this.id).isEqual(new ErrorType())) {
            errors.add(new SemanticError("Variabile " + this.id + " non dichiarata"));
        }

        return new ArrayList<SemanticError>();
    }

    @Override
    public Type typeCheck() {
        return null;
    }

    @Override
    public String codeGeneration() {
        return null;
    }
}
