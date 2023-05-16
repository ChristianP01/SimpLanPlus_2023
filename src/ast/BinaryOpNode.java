package ast;

import ast.types.ErrorType;
import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;

import java.util.ArrayList;

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
        if(Type.isEqual(this.left.typeCheck(), this.operandsType) && Type.isEqual(this.right.typeCheck(), this.operandsType)) {
            return this.returnType;
        } else {
            System.out.println("Type mismatch: non-" + this.operandsType.getClass().getName() + " in " + this.opName);
            return new ErrorType();
        }
    }

    @Override
    public String codeGeneration() {
        return null;
    }
}