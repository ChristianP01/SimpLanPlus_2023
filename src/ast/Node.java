package ast;

import ast.types.Type;
import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;

import java.util.ArrayList;

public interface Node {
    public ArrayList<SemanticError> checkSemantics(SymbolTable symTable, int nesting);
    public Type typeCheck();

    public String codeGeneration();

    public String toPrint(String s);
}