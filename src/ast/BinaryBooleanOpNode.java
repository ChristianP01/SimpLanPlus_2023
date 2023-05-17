package ast;

import ast.types.BoolType;
import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;

import java.util.ArrayList;

public class BinaryBooleanOpNode extends BinaryOpNode {

    private String opCode;

    public BinaryBooleanOpNode(Node left, Node right, Type returnType, String opCode) {
        super(left, right, new BoolType(), returnType, opCode);
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
        return "";
    }
}
