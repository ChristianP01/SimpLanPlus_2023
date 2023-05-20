package ast.expressions;

import ast.Node;
import ast.Type;
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
        if(this.left.typeCheck().isEqual(this.operandsType) && this.right.typeCheck().isEqual(this.operandsType)) {
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
}