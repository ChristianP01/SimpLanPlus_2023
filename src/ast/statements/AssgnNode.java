package ast.statements;

import ast.Node;
import ast.Type;
import ast.types.ErrorType;
import ast.types.VoidType;
import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;

public class AssgnNode implements Node {
    private Node id;
    private Node exp;

    public AssgnNode(Node id, Node exp) {
        this.id = id;
        this.exp = exp;
    }


    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable symTable, int nesting) {

        return new ArrayList<SemanticError>();
    }

    @Override
    public Type typeCheck() {
        if(this.id.typeCheck().isEqual(this.exp.typeCheck())) return new VoidType();

        return new ErrorType();
    }

    @Override
    public String codeGeneration() {
        return null;
    }
}
