package ast;

import ast.types.BoolType;

public class BinaryBooleanOpNode extends BinaryOpNode {

    public BinaryBooleanOpNode(Node left, Node right, Type returnType, String opName) {
        super(left, right, new BoolType(), returnType, opName);
    }

    @Override
    public String codeGeneration() {
        return "";
    }
}
