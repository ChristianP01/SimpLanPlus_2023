package ast.expressions;

import ast.Node;
import ast.types.BoolType;

public class GreaterEqualNode extends BinaryIntegerOpNode {
    public GreaterEqualNode(Node left, Node right) {
        super(left, right, new BoolType(), ">=");
    }

    @Override
    public String codeGeneration() {
        return null;
    }
}
