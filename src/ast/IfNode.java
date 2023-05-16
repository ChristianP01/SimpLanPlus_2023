package ast;

import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;

import java.util.ArrayList;

public class IfNode implements Node {
    private Node condition;
    private Node thenNode;
    private Node elseNode;

    public IfNode(Node condition, Node thenNode, Node elseNode) {
        this.condition = condition;
        this.thenNode = thenNode;
        this.elseNode = elseNode;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable symTable, int nesting) {
        ArrayList<SemanticError> errors = new ArrayList<SemanticError>();

        errors.addAll(this.condition.checkSemantics(symTable, nesting));
        errors.addAll(this.thenNode.checkSemantics(symTable, nesting));
        errors.addAll(this.elseNode.checkSemantics(symTable, nesting));


        return errors;
    }

    @Override
    // TODO
    public Type typeCheck() {
        return null;
    }

    @Override
    public String codeGeneration() {
        return null;
    }
}
