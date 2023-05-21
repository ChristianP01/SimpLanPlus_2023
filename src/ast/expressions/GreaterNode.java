package ast.expressions;

import ast.Node;
import ast.types.BoolType;

public class GreaterNode extends BinaryIntegerOpNode {
    public GreaterNode(Node left, Node right) {
        super(left, right, new BoolType(), ">");
    }

    @Override
    public String codeGeneration() {
        return null;
    }
}
