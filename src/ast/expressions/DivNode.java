package ast.expressions;

import ast.Node;
import ast.types.IntType;

public class DivNode extends BinaryIntegerOpNode {
    public DivNode(Node left, Node right) {
        super(left, right, new IntType(),"division");
    }

    @Override
    public String codeGeneration() {
        return left.codeGeneration() +
                "pushr A0 \n" +
                right.codeGeneration() +
                "popr T1 \n" +
                "div T1 A0 \n" +
                "popr A0 \n";
    }
}
