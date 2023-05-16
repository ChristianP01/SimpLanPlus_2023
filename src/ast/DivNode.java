package ast;

import ast.types.IntType;

public class DivNode extends BinaryIntegerOpNode {
    public DivNode(Node left, Node right) {
        super(left, right, new IntType(),"division");
    }

    @Override
    public String codeGeneration() {
        // TODO implementare generazione di codice
        return super.codeGeneration();
    }
}
