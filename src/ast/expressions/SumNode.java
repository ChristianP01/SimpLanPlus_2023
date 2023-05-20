package ast.expressions;

import ast.Node;
import ast.types.IntType;

public class SumNode extends BinaryIntegerOpNode {
    public SumNode(Node left, Node right) {
        super(left, right, new IntType(),"addition");
    }

    @Override
    public String codeGeneration() {
        // TODO implementare generazione di codice
        return super.codeGeneration();
    }
}