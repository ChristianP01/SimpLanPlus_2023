package ast;

import ast.types.BoolType;

public class OrNode extends BinaryBooleanOpNode {
    public OrNode(Node left, Node right) {
        super(left, right, new BoolType(), "or");
    }

    @Override
    public String codeGeneration() {
        // TODO implementare generazione di codice

        return "";
    }
}
