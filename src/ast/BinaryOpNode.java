package ast;

import ast.types.IntType;
import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;

import java.util.ArrayList;

public class BinaryOpNode implements Node {
    private Node left;
    private Node right;
    private String opName;

    public BinaryOpNode(Node left, Node right, String opName) {
        this.left = left;
        this.right = right;
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
        if(this.left.typeCheck() instanceof IntType && this.right.typeCheck() instanceof IntType) {
            return new IntType();
        } else {
            // TODO aggiungere tipo errore
            System.out.println("Type error: non integers in " + this.opName);
            return new Type();
        }
    }

    @Override
    public String codeGeneration() {
        return null;
    }
}
