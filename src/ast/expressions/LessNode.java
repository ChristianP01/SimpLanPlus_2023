package ast.expressions;

import ast.Node;
import ast.types.BoolType;

public class LessNode extends BinaryIntegerOpNode {
    public LessNode(Node left, Node right) {
        super(left, right, new BoolType(), "<");
    }

    @Override
    public String codeGeneration() {
        return null;
    }
}