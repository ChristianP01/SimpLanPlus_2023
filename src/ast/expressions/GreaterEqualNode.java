package ast.expressions;

import ast.Node;
import ast.simplanlib.SimplanInterface;
import ast.types.BoolType;

public class GreaterEqualNode extends BinaryIntegerOpNode {
    public GreaterEqualNode(Node left, Node right) {
        super(left, right, new BoolType(), ">=");
    }

    @Override
    public String codeGeneration() {
        String trueLabel = SimplanInterface.newLabel();
        String exitLabel = SimplanInterface.newLabel();

        return this.left.codeGeneration() +
                "pushr A0 \n" +
                this.right.codeGeneration() +
                "popr T1 \n" +
                "bleq A0 T1 " + trueLabel + "\n" + //Right <= Left -> Left >= Right ->
                "storei A0 0 \n" +
                "b " + exitLabel + "\n" +
                trueLabel + ": \n" +
                "storei A0 1 \n" +
                exitLabel + ": \n";
    }
}
