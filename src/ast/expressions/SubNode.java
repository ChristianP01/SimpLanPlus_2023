package ast.expressions;

import ast.Node;
import ast.types.IntType;
import ast.types.Type;
import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;

import java.util.ArrayList;

public class SubNode extends BinaryIntegerOpNode {
    public SubNode(Node left, Node right) {
        super(left, right, new IntType(), "subtraction");
    }

    @Override
    public Type typeCheck() {
        return super.typeCheck();
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable symTable, int nesting) {
        return super.checkSemantics(symTable, nesting);
    }

    @Override
    public String codeGeneration() {
        return left.codeGeneration() +
                "pushr A0 \n" +
                right.codeGeneration() +
                "popr T1 \n" +
                "sub T1 A0 \n" +
                "popr A0 \n";
    }
}
