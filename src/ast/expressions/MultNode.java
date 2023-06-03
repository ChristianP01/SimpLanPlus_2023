package ast.expressions;

import ast.Node;
import ast.types.IntType;

public class MultNode extends BinaryIntegerOpNode {
    public MultNode(Node left, Node right) {
        super(left, right, new IntType(),"multiplication");
    }

    @Override
    public String codeGeneration() {
        return left.codeGeneration() +
                "pushr A0 \n" +
                right.codeGeneration() +
                "popr T1 \n" +
                "mul A0 T1 \n" +
                "popr A0 \n";
    }
}
