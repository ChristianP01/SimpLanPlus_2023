package ast.types;

public class IntType extends Type {
    public IntType() {}

    @Override
    public String toString() {
        return "int";
    }

    @Override
    public String toPrint(String s) {
        return s + "int ";
    }
}
