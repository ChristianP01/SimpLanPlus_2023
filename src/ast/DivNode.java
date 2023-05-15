package ast;

public class DivNode extends BinaryIntegerOpNode {
    public DivNode(Node left, Node right) {
        super(left, right, "division");
    }

    @Override
    public String codeGeneration() {
        // TODO implementare generazione di codice
        return super.codeGeneration();
    }
}
