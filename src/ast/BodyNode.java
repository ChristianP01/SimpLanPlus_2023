package ast;

import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;

import java.util.ArrayList;

public class BodyNode implements Node {
    private ArrayList<Node> dec;
    private ArrayList<Node> stm;
    private Node exp;

    public BodyNode(ArrayList<Node> dec, ArrayList<Node> stm, Node exp) {
        this.dec = dec;
        this.stm = stm;
        this.exp = exp;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable symTable, int nesting) {
        ArrayList<SemanticError> errors = new ArrayList<>();

        for (Node d : dec) {
            errors.addAll(d.checkSemantics(symTable, nesting));
        }

        for (Node s : stm) {
            errors.addAll(s.checkSemantics(symTable, nesting));
        }

        errors.addAll(exp.checkSemantics(symTable, nesting));
        return errors;
    }

    @Override
    public Type typeCheck() {
        return null;
    }

    @Override
    public String codeGeneration() {
        return null;
    }
}
