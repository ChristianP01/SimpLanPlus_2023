package ast;

public class MultNode extends BinaryIntegerOpNode {
    public MultNode(Node left, Node right) {
        super(left, right, "multiplication");
    }

    @Override
    public String codeGeneration() {
        // TODO implementare generazione di codice
        return super.codeGeneration();
    }
}
