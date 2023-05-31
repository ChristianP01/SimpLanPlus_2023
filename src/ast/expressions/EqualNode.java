package ast.expressions;

import ast.Node;
import ast.simplanlib.SimplanInterface;
import ast.types.Type;
import ast.types.BoolType;
import ast.types.ErrorType;
import ast.types.FunType;
import ast.types.VoidType;
import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;

import java.util.ArrayList;

public class EqualNode implements Node {

    private Node left;
    private Node right;

    public EqualNode(Node left, Node right) {
        this.left = left;
        this.right = right;
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
        Type leftType = left.typeCheck();
        Type rightType = right.typeCheck();
        // si controlla per prima cosa che entrambi gli operandi non siano void o funzioni
        if(leftType instanceof FunType || leftType instanceof VoidType) {
            System.out.println("Cannot compare expressions of type " + leftType.toString() + ".");
            return new ErrorType();
        } else if(rightType instanceof FunType || rightType instanceof VoidType) {
            System.out.println("Cannot compare expressions of type " + rightType.toString() + ".");
            return new ErrorType();
            // se i tipi non combaciano, si ritorna errore
        } else if(!leftType.isEqual(rightType)) {
            System.out.println("Cannot compare expressions of different types, got " + leftType.toString() +
                    " and " + rightType.toString() + ".");
        }

        return new BoolType();
    }

    @Override
    public String codeGeneration() {
        String eqLabel = SimplanInterface.newLabel();
        String exitLabel = SimplanInterface.newLabel();
        return this.left.codeGeneration() +
                "pushr A0\n" +
                this.right.codeGeneration() +
                "popr T1\n" +
                "beq A0 T1 " + eqLabel + "\n" +
                "storei A0 0\n" +
                "b " + exitLabel + "\n" +
                eqLabel + ":\n" +
                "storei A0 1\n" +
                exitLabel + ":\n";
    }

    @Override
    public String toPrint(String s) {
        return s + "==\n" + this.left.toPrint(s + "\t") + this.right.toPrint(s + "\t");
    }
}
