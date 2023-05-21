package ast.types;

public class ErrorType extends Type {
    public ErrorType() { }

    @Override
    public String toString() {
        return "error";
    }
}
