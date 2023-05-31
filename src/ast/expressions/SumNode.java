package ast.expressions;

import ast.Node;
import ast.types.IntType;

public class SumNode extends BinaryIntegerOpNode {
    public SumNode(Node left, Node right) {
        super(left, right, new IntType(),"addition");
    }

    @Override
    public String codeGeneration() {
        return this.left.codeGeneration() +
                "pushr A0\n" +
                this.right.codeGeneration() +
                "popr T1\n" +
                "add A0 T1\n" +
                "popr A0";
    }
}
