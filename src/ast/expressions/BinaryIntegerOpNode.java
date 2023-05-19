package ast.expressions;

import ast.Node;
import ast.Type;
import ast.types.IntType;
import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;

import java.util.ArrayList;

public class BinaryIntegerOpNode extends BinaryOpNode {
    private String opCode;

    public BinaryIntegerOpNode(Node left, Node right, Type returnType, String opCode) {
        super(left, right, new IntType(), returnType, opCode);
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
        return null;
    }
}
