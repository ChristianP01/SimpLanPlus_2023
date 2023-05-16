package ast.types;

import ast.Type;

public class ErrorType extends Type {
    public ErrorType() { }

    @Override
    public String toString() {
        return "error";
    }
}
