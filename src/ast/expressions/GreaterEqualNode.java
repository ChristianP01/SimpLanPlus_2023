package ast.expressions;

import ast.Node;
import ast.Type;
import ast.types.BoolType;
import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;

import java.util.ArrayList;

public class GreaterEqualNode extends BinaryIntegerOpNode {
    public GreaterEqualNode(Node left, Node right) {
        super(left, right, new BoolType(), ">=");
    }

    @Override
    public String codeGeneration() {
        return null;
    }
}
