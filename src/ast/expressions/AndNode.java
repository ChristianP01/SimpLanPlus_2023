package ast.expressions;

import ast.Node;
import ast.simplanlib.SimplanInterface;
import ast.types.BoolType;

public class AndNode extends BinaryBooleanOpNode {
    public AndNode(Node left, Node right) {
        super(left, right, new BoolType(), "and");
    }

    @Override
    public String codeGeneration() {
        String exitLabel = SimplanInterface.newLabel();
        return left.codeGeneration() +
                "beq A0 0 " + exitLabel + "\n" +
                right.codeGeneration() +
                exitLabel + ": \n";
    }
}