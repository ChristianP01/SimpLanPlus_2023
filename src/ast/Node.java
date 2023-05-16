package ast;

import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;

import java.util.ArrayList;

public interface Node {
    ArrayList<SemanticError> checkSemantics(SymbolTable symTable, int nesting);
    Type typeCheck();

    String codeGeneration();
}

/**
 * 100 pushr A0
 * 108 jsub label
 * 112 
 *
 *
 * 9000 label:
 * ...
 *
 */

FP
xn
x3
x2
x1
