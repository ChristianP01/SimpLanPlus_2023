package ast.expressions;

import ast.Node;
import ast.simplanlib.SimplanInterface;
import ast.types.BoolType;

public class LessEqualNode extends BinaryIntegerOpNode {
    public LessEqualNode(Node left, Node right) {
        super(left, right, new BoolType(), "<=");

    }

    @Override
    public String codeGeneration() {
        String eqLabel = SimplanInterface.newLabel();
        String exitLabel = SimplanInterface.newLabel();
        return this.left.codeGeneration() +
                "pushr A0\n" +
                this.right.codeGeneration() +
                "popr T1\n" +
                "bleq T1 A0 " + eqLabel + "\n" +
                "storei A0 0\n" +
                "b " + exitLabel + "\n" +
                eqLabel + ":\n" +
                "storei A0 1\n" +
                exitLabel + ":\n";
    }
}
