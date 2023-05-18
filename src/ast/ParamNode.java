package ast;

import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;

import java.util.ArrayList;

public class ParamNode implements Node {
    private Node type;
    private String id;

    public ParamNode(Node type, String id) {
        this.type = type;
        this.id = id;
    }

    public Type getType() {
        return (Type) this.type;
    }

    public String getId() {
        return id;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable symTable, int nesting) {
        // non ritorna errori di semantica, è possibile usare un nome già utilizzato
        return new ArrayList<SemanticError>();
    }

    @Override
    public Type typeCheck() {
        // non ha tipo
        return null;
    }

    @Override
    public String codeGeneration() {
        // TODO implementare generazione di codice
        return null;
    }
}
