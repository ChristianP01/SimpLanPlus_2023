package ast.expressions;

import ast.Node;
import ast.types.IntType;
import ast.types.Type;
import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;

import java.util.ArrayList;

public class SumNode extends BinaryIntegerOpNode {
    public SumNode(Node left, Node right) {
        super(left, right, new IntType(),"addition");
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable symTable, int nesting) {
        return super.checkSemantics(symTable, nesting);
    }

    @Override
    public Type typeCheck() {
        return super.typeCheck();
    }

    @Override
    public String codeGeneration() {
        return this.left.codeGeneration() +
                "pushr A0\n" +
                this.right.codeGeneration() +
                "popr T1\n" +
                "add A0 T1\n" +
                "popr A0\n";
    }
}
