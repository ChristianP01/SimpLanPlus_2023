package ast.declarations;

import ast.Node;
import ast.Type;
import ast.types.VoidType;
import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;

import java.util.ArrayList;

public class DecNode implements Node {
    private Node type;
    private String id;

    public DecNode(Node type, String id) {
        this.type = type;
        this.id = id;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable symTable, int nesting) {
        ArrayList<SemanticError> errors = new ArrayList<SemanticError>();
        // controllo che non sia già stato dichiarato un identificatore con lo stesso nome
        if(symTable.topLookup(this.id)) {
            errors.add(new SemanticError("Identifier " + this.id + " already declared in current scope."));
        } else {
            symTable.insert(this.id, (Type) this.type);
        }

        return errors;
    }

    @Override
    public Type typeCheck() {
        return null;
    }

    @Override
    public String codeGeneration() {
        // TODO implementare generazione di codice
        return null;
    }
}
