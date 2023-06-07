package ast.declarations;

import ast.Node;
import ast.types.Type;
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

    public String getId() {
        return this.id;
    }

    @Override
    public Type typeCheck() {
        return (Type) this.type;
    }

    @Override
    public String codeGeneration() {
        return "subi SP 1\n";
    }

    @Override
    public String toPrint(String s) {
        return s + "Var dec: " + this.type.toPrint("") + " " + this.id + "\n";
    }
}
