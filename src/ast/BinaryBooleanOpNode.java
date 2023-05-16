package ast;

import ast.types.BoolType;
import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;

import java.util.ArrayList;

public class BinaryBooleanOpNode extends BinaryOpNode {

    private enum operationCode {
        GreaterThan,
        LessThan,
        GreaterEqualThan,
        LessEqualThan,
        EqualThan,
        And,
        Or
    }

    private operationCode opCode;

    public BinaryBooleanOpNode(Node left, Node right, Type returnType, operationCode opCode) {
        super(left, right, new BoolType(), returnType, opCode.toString());
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
