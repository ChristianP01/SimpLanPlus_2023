package ast.expressions;

import ast.Node;
import ast.simplanlib.SimplanInterface;
import ast.types.BoolType;
import ast.types.Type;
import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;

import java.util.ArrayList;

public class LessNode extends BinaryIntegerOpNode {
    public LessNode(Node left, Node right) {
        super(left, right, new BoolType(), "<");
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
        String true_branch = SimplanInterface.newLabel();
        String false_branch = SimplanInterface.newLabel();

        return left.codeGeneration() +
                "pushr A0 \n" +
                right.codeGeneration() +
                "popr T1 \n" +
                "bleq T1 A0 " + true_branch + "\n" + // Left <= right
                false_branch + ":\n" +
                "storei A0 0\n" +
                "b " + exitLabel + "\n" +
                true_branch + ":\n" +
                "beq A0 T1 " + false_branch + "\n" +
                "storei A0 1\n" +
                exitLabel + ":\n";
    }
}