package ast.expressions;

import ast.Node;
import ast.simplanlib.SimplanInterface;
import ast.types.BoolType;
import ast.types.Type;

public class AndNode extends BinaryBooleanOpNode {
    public AndNode(Node left, Node right) {
        super(left, right, new BoolType(), "and");
    }

    @Override
    public Type typeCheck() {
        return super.typeCheck();
    }

    @Override
    public String codeGeneration() {
        String exitLabel = SimplanInterface.newLabel();
        return left.codeGeneration() +
                "storei T1 0\n" +
                "beq A0 T1 " + exitLabel + "\n" +
                right.codeGeneration() +
                exitLabel + ": \n";
    }
}