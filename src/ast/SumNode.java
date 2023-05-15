package ast;

public class SumNode extends BinaryIntegerOpNode {
    public SumNode(Node left, Node right) {
        super(left, right, "addition");
    }

    @Override
    public String codeGeneration() {
        // TODO implementare generazione di codice
        return super.codeGeneration();
    }
}
