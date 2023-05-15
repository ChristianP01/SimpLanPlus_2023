package ast;

import ast.types.BoolType;

public class BinaryBooleanOpNode extends BinaryOpNode {

    public BinaryBooleanOpNode(Node left, Node right, String opName) {
        super(left, right, new BoolType(), opName);
    }

    @Override
    public String codeGeneration() {
        return "";
    }
}
