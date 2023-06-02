package ast.expressions;

import ast.Node;
import ast.simplanlib.SimplanInterface;
import ast.types.BoolType;

public class OrNode extends BinaryBooleanOpNode {
    public OrNode(Node left, Node right) {
        super(left, right, new BoolType(), "or");
    }

    @Override
    public String codeGeneration() {
        String exitLabel = SimplanInterface.newLabel();
        return this.left.codeGeneration() +
                "storei T1 1\n" +
                "beq A0 T1 " + exitLabel + "\n" +
                this.right.codeGeneration() +
                exitLabel + ":\n";
    }
}
