package ast.statements;

import ast.Node;
import ast.types.Type;
import ast.expressions.FunCallNode;
import ast.types.VoidType;

import java.util.ArrayList;

public class FunCallStmNode extends FunCallNode {
    public FunCallStmNode(String id, ArrayList<Node> params) {
        super(id, params);
    }

    @Override
    public Type typeCheck() {
        // ignora il tipo di ritorno e ritorna void
        super.typeCheck();
        return new VoidType();
    }
}
