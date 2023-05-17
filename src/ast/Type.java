package ast;

import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;

import java.util.ArrayList;

public abstract class Type {

    // metodo statico per l'equivalenza tra tipi
    public boolean isEqual(Type A) {
        return this.getClass().equals(A.getClass());
    }

    public abstract String toString();

}
