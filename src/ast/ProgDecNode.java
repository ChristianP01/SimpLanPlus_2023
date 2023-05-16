package ast;

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

        for (Node d : dec) {
            d.checkSemantics(symTable, nesting);
            errors.addAll(d.checkSemantics(symTable, nesting));
        }
    }

    @Override
    public Type typeCheck() {

    }

    @Override
    public String codeGeneration() {
        // TODO implementare la generazione di codice
        return null;
    }

}
