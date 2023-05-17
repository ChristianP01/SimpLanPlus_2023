package ast;

import ast.types.BoolType;

public class AndNode extends BinaryBooleanOpNode {
    public AndNode(Node left, Node right) {
        super(left, right, new BoolType(), "and");
    }

    @Override
    public String codeGeneration() {
        // TODO implementare generazione di codice
        return "";
    }
}
