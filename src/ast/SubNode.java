package ast;

public class SubNode extends BinaryIntegerOpNode {
    public SubNode(Node left, Node right) {
        super(left, right, "subtraction");
    }

    @Override
    public String codeGeneration() {
        // TODO implementare generazione di codice
        return super.codeGeneration();
    }
}
