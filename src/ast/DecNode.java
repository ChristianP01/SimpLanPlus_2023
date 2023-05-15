package ast;

import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;

import java.util.ArrayList;

public class DecNode implements Node {
    private Type type;
    private String id;

    public DecNode(Type type, String id) {
        this.type = type;
        this.id = id;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable symTable, int nesting) {
        ArrayList<SemanticError> errors = new ArrayList<SemanticError>();
        // controllo che non sia già stato dichiarato un identificatore con lo stesso nome
        if(symTable.topLookup(this.id)) {
            errors.add(new SemanticError("Variabile " + this.id + " già dichiarata."));
        } else {
            // TODO inserimento nella symbol table del nuovo simbolo
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
