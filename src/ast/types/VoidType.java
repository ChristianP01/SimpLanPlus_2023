package ast.types;

public class VoidType extends Type {
    public VoidType() { }

    @Override
    public String toString() {
        return "void";
    }

    @Override
    public String toPrint(String s) {
        return s + "void ";
    }
}
