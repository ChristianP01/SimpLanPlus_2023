package ast.expressions;

import ast.Node;
import ast.Type;
import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;

import java.util.ArrayList;

public class IfBodyNode implements Node {
    private ArrayList<Node> stms;
    private Node exp;

    public IfBodyNode(ArrayList<Node> stms, Node exp) {
        this.stms = stms;
        this.exp = exp;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable symTable, int nesting) {
        ArrayList<SemanticError> errors = new ArrayList<SemanticError>();

        for (Node stm : this.stms) {
            errors.addAll(stm.checkSemantics(symTable, nesting));
        }

        errors.addAll(this.exp.checkSemantics(symTable, nesting));
        return errors;
    }

    @Override
    public Type typeCheck() {
        return this.exp.typeCheck();
    }

    @Override
    public String codeGeneration() {
        return null;
    }
}