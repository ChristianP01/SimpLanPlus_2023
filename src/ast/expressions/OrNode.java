package ast.expressions;

import ast.Node;
import ast.simplanlib.SimplanInterface;
import ast.types.BoolType;
import ast.types.Type;
import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;

import java.util.ArrayList;

public class OrNode extends BinaryBooleanOpNode {
    public OrNode(Node left, Node right) {
        super(left, right, new BoolType(), "or");
    }

    @Override
    public Type typeCheck() {
        return super.typeCheck();
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable symTable, int nesting) {
        return super.checkSemantics(symTable, nesting);
    }

    @Override
    public String codeGeneration() {
        String exitLabel = SimplanInterface.newLabel();
        return this.left.codeGeneration() +
                "storei T1 1\n" +
                "beq A0 T1 " + exitLabel + "\n" +
                this.right.codeGeneration() +
                exitLabel + ":\n";
    }
}
