package ast;

import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;

import java.util.ArrayList;

public interface Node {
    ArrayList<SemanticError> checkSemantics(SymbolTable symTable, int nesting);
    Type typeCheck();

    String codeGeneration();
}