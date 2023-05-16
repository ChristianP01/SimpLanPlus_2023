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

    public static boolean isEqual(Type A, Type B) {
        return A.getClass().equals(B.getClass());
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
