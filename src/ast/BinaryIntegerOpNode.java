package ast;

import ast.types.IntType;
import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;

import java.util.ArrayList;

public class BinaryIntegerOpNode extends BinaryOpNode {

    private enum operationCode {
        Addition,
        Substraction,
        Multiplication,
        Division
    }

    private operationCode opCode;

    public BinaryIntegerOpNode(Node left, Node right, Type returnType, operationCode opCode) {
        super(left, right, new IntType(), returnType, opCode.toString());
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
