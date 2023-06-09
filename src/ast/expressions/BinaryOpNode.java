package ast.expressions;

import ast.Node;
import ast.types.Type;
import ast.types.ErrorType;
import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;

import java.util.ArrayList;
import java.util.Objects;

public class BinaryOpNode implements Node {
    protected final Node left;
    protected final Node right;
    private final Type operandsType;
    private final Type returnType;
    protected final String opName;

    public BinaryOpNode(Node left, Node right, Type operandsType, Type returnType, String opName) {
        this.left = left;
        this.right = right;
        this.operandsType = operandsType;
        this.returnType = returnType;
        this.opName = opName;

    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable symTable, int nesting) {
        ArrayList<SemanticError> errors = new ArrayList<SemanticError>();
        errors.addAll(this.left.checkSemantics(symTable, nesting));
        errors.addAll(this.right.checkSemantics(symTable, nesting));
        return errors;
    }

    @Override
    public Type typeCheck() {
        Type leftType = this.left.typeCheck();
        Type rightType = this.right.typeCheck();

        if(Objects.equals(leftType.toString(), this.operandsType.toString()) &&
                Objects.equals(rightType.toString(), this.operandsType.toString())) {
            return this.returnType;
        } else {
            System.out.println("Type mismatch: non-" + this.operandsType.toString() + " in " + this.opName);
            return new ErrorType();
        }
    }

    @Override
    public String codeGeneration() {
        return null;
    }

    @Override
    public String toPrint(String s) {
        return s + this.opName + "\n" + this.left.toPrint(s + "\t") + this.right.toPrint(s + "\t");
    }
}