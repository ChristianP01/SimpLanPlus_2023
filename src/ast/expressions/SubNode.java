package ast.expressions;

import ast.Node;
import ast.types.IntType;

public class SubNode extends BinaryIntegerOpNode {
    public SubNode(Node left, Node right) {
        super(left, right, new IntType(), "subtraction");
    }

    @Override
    public String codeGeneration() {
        return left.codeGeneration() +
                "pushr A0 \n" +
                right.codeGeneration() +
                "popr T1 \n" +
                "sub T1 A0 \n" +
                "pushr A0 \n";
    }
}
