package ast;

import ast.types.VoidType;
import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;

import java.util.ArrayList;

public class ProgDecNode implements Node {
    private ArrayList<Node> dec;
    private ArrayList<Node> stm;
    private Node exp;

    public ProgDecNode(ArrayList<Node> dec, ArrayList<Node> stm, Node exp) {
        this.dec = dec;
        this.stm = stm;
        this.exp = exp;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable symTable, int nesting) {

        ArrayList<SemanticError> errors = new ArrayList<SemanticError>();

        for (Node d : this.dec) {
            d.checkSemantics(symTable, nesting);
            errors.addAll(d.checkSemantics(symTable, nesting));
        }

        for (Node s : this.stm) {
            s.checkSemantics(symTable, nesting);
            errors.addAll(s.checkSemantics(symTable, nesting));
        }

        if(this.exp != null)
            errors.addAll(exp.checkSemantics(symTable, nesting));

        return errors;
    }

    @Override
    public Type typeCheck() {
        this.dec.stream().map(Node::typeCheck);
        this.stm.stream().map(Node::typeCheck);

        return this.exp != null ? this.exp.typeCheck() : new VoidType();
    }

    @Override
    public String codeGeneration() {
        // TODO implementare la generazione di codice
        return null;
    }

}
