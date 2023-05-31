package ast.expressions;

import ast.Node;
import ast.simplanlib.SimplanInterface;
import ast.types.BoolType;

public class GreaterNode extends BinaryIntegerOpNode {
    public GreaterNode(Node left, Node right) {
        super(left, right, new BoolType(), ">");
    }

    @Override
    public String codeGeneration() {
        String exitLabel = SimplanInterface.newLabel();
        String true_branch = SimplanInterface.newLabel();
        String false_branch = SimplanInterface.newLabel();

        return left.codeGeneration() +
                "pushr A0 \n" +
                right.codeGeneration() +
                "popr T1 \n" +
                "bleq A0 T1 " + true_branch + "\n" + // Right <= Left -> Left >= Right
                false_branch + ":\n" +
                "storei A0 0\n" +
                "b " + exitLabel + "\n" +
                true_branch + ":\n" +
                "beq A0 T1 " + false_branch + "\n" +
                "storei A0 1\n" +
                exitLabel + ":\n";
    }
}
