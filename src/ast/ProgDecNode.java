package ast;

import ast.simplanlib.SimplanInterface;
import ast.types.Type;
import ast.types.VoidType;
import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;

import java.util.ArrayList;
import java.util.stream.Collectors;

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
            errors.addAll(d.checkSemantics(symTable, nesting));
        }

        for (Node s : this.stm) {
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
        StringBuilder codegen = new StringBuilder();
        // necessario per far funzionare gli offset
        codegen.append("pushr FP\n");
        codegen.append("pushr AL\n");
        for(Node dec : this.dec) {
           codegen.append(dec.codeGeneration());
        }

        for (Node stm : this.stm) {
            codegen.append(stm.codeGeneration());
        }

        codegen.append(this.exp == null ? "" : this.exp.codeGeneration());

        return codegen + "halt \n" + SimplanInterface.getCode();
    }

    @Override
    public String toPrint(String s) {
        String print = "ProgDec\n";
        print += this.dec.stream().map(d -> d.toPrint("\t")).collect(Collectors.joining(""));
        print += this.stm.stream().map(st -> st.toPrint("\t")).collect(Collectors.joining(""));
        if(this.exp != null)
            print += this.exp.toPrint("\t");

        return print;
    }

}
