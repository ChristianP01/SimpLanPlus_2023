package ast;

import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;

import java.util.ArrayList;

public class ProgDecNode extends ProgNode {

    private ArrayList<Node> dec;
    private ArrayList<Node> stm;

    public ProgDecNode(ArrayList<Node> dec, ArrayList<Node> stm, Node exp) {
        super(exp);

        this.dec = dec;
        this.stm = stm;
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

        errors.addAll(super.checkSemantics(symTable, nesting));
        return errors;
    }

    @Override
    public Type typeCheck() {
        return null;
    }

    @Override
    public String codeGeneration() {
        // TODO implementare la generazione di codice
        return null;
    }

}