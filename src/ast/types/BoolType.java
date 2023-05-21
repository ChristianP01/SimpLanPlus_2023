package ast.types;

public class BoolType extends Type {
    public BoolType() { }

    @Override
    public String toString() {
        return "bool";
    }

    @Override
    public String toPrint(String s) {
        return s + "bool ";
    }
}
