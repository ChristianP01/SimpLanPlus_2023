package ast;

import ast.declarations.DecNode;
import ast.simplanlib.SimplanInterface;
import ast.types.ErrorType;
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
        for (Node dec : this.dec) {
            if (dec.typeCheck() instanceof ErrorType)
                return new ErrorType();
        }

        for (Node stm : this.stm) {
            if (stm.typeCheck() instanceof ErrorType)
                return new ErrorType();
        }

        if (this.exp == null) {
            return new VoidType();
        } else {
            return this.exp.typeCheck();
        }
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

        // svuotamento dello stack

        // ottengo il numero di variabili dichiarate (le funzioni non occupano spazio sullo stack)
        int numVarDec = this.dec.stream().filter(d -> d instanceof DecNode).toList().size();
        codegen.append("addi SP ").append(numVarDec + 2).append("\n");

        return codegen + "halt\n" + SimplanInterface.getCode();
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
