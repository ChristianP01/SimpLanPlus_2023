package ast.statements;

import ast.Node;
import ast.types.Type;
import ast.types.ErrorType;
import ast.types.VoidType;
import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;

import java.util.ArrayList;

public class AssgnNode implements Node {
    private Node id;
    private Node exp;

    public AssgnNode(Node id, Node exp) {
        this.id = id;
        this.exp = exp;
    }


    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable symTable, int nesting) {
        // TODO modificare per aggiungere alla symbol table che la variabile è ora utilizzata
        ArrayList<SemanticError> errors = new ArrayList<SemanticError>();

        errors.addAll(this.id.checkSemantics(symTable, nesting));
        errors.addAll(this.exp.checkSemantics(symTable, nesting));

        return errors;
    }

    @Override
    public Type typeCheck() {
        if(this.id.typeCheck().isEqual(this.exp.typeCheck())) return new VoidType();

        return new ErrorType();
    }

    @Override
    public String codeGeneration() {
        return null;
    }
}
