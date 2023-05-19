package ast.expressions;

import ast.Node;
import ast.Type;
import ast.expressions.IdNode;
import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;

import java.util.ArrayList;

public class FunNode extends IdNode {

    private ArrayList<Node> params;
    private ArrayList<Node> exps;

    public FunNode(String id, ArrayList<Node> params) {
        super(id);
        this.params = params;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable symTable, int nesting) {

        ArrayList<SemanticError> errors = new ArrayList<>();

        for (Node param : params) {
            errors.addAll(param.checkSemantics(symTable, nesting));
        }

        for (Node e : exps) {
            errors.addAll(e.checkSemantics(symTable, nesting));
        }

        return errors;

    }

    @Override
    public Type typeCheck() {
        return super.typeCheck();
    }

    @Override
    public String codeGeneration() {
        return super.codeGeneration();
    }
}
