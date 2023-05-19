package ast.expressions;

import ast.Node;
import ast.types.IntType;

public class SubNode extends BinaryIntegerOpNode {
    public SubNode(Node left, Node right) {
        super(left, right, new IntType(), "subtraction");
    }

    @Override
    public String codeGeneration() {
        // TODO implementare generazione di codice
        return super.codeGeneration();
    }
}
