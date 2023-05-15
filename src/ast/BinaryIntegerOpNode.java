package ast;

import ast.types.ErrorType;
import ast.types.IntType;

public class BinaryIntegerOpNode extends BinaryOpNode {

    public BinaryIntegerOpNode(Node left, Node right, String opName) {
        super(left, right, new IntType(), opName);
    }

    @Override
    public String codeGeneration() {
        return null;
    }
}
