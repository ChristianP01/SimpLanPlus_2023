package ast;

import ast.types.IntType;

public class BinaryIntegerOpNode extends BinaryOpNode {

    public BinaryIntegerOpNode(Node left, Node right, Type returnType, String opName) {
        super(left, right, new IntType(), returnType, opName);
    }

    @Override
    public String codeGeneration() {
        return null;
    }
}
