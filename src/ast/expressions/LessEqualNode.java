package ast.expressions;

import ast.Node;
import ast.types.BoolType;

public class LessEqualNode extends BinaryIntegerOpNode {
    public LessEqualNode(Node left, Node right) {
        super(left, right, new BoolType(), "<=");

    }

    @Override
    public String codeGeneration() {
        return null;
    }
}
