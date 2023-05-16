package ast;

import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;

import java.util.ArrayList;

public class Type implements Node {

    public enum typeEnum {
        Int,
        Bool,
        Fun,
        Void,
        Error
    }

    public typeEnum type;

    // metodo statico per l'equivalenza tra tipi
    public boolean isEqual(Type A) {
        return this.getClass().equals(A.getClass());
    }

    public String toString() {
        return "Type: " + this.type.toString();
    }

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
        return "";
    }
}
