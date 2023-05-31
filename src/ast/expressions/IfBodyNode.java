package ast.expressions;

import ast.Node;
import ast.types.Type;
import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;

import java.util.ArrayList;
import java.util.stream.Collectors;

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
        StringBuilder codegen = new StringBuilder();

        for (Node stm : stms)
            codegen.append(stm.codeGeneration());

        codegen.append(this.exp == null ? "" : this.exp.codeGeneration());

        return codegen.toString();
    }

    @Override
    public String toPrint(String s) {
        return s + this.stms.stream()
                .map(st -> st.toPrint(s + "\t")).collect(Collectors.joining("\n")) +
                this.exp.toPrint(s + "\t");
    }
}
