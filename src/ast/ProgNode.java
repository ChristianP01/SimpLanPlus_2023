package ast;

import ast.simplanlib.SimplanInterface;
import ast.types.Type;
import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;

import java.util.ArrayList;

public class ProgNode implements Node {
    protected Node exp;

    public ProgNode(Node exp) {
        this.exp = exp;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable symTable, int nesting) {
        return exp.checkSemantics(symTable, nesting);
    }

    @Override
    public Type typeCheck() {
        return exp.typeCheck();
    }

    @Override
    public String codeGeneration() {
        return this.exp == null ? "" : this.exp.codeGeneration() + "halt \n";
    }

    @Override
    public String toPrint(String s) {
        return "Prog\n" + this.exp.toPrint("\t");
    }
}
