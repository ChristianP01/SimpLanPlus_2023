package ast;

import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;
import java.util.ArrayList;

public class DecFunNode extends DecNode {

    private ArrayList<Node> params;
    private BodyNode funBody;

    public DecFunNode(Type type, String id, ArrayList<Node> params, BodyNode funBody) {
        super(type, id);

        this.params = params;
        this.funBody = funBody;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable symTable, int nesting) {

        ArrayList<SemanticError> errors = new ArrayList<>();

        for (Node param : params) {
            errors.addAll(param.checkSemantics(symTable, nesting));
        }

        errors.addAll(funBody.checkSemantics(symTable, nesting));

        return errors;

    }

    @Override
    public Type typeCheck() {
        return super.typeCheck();
    }

    @Override
    public String codeGeneration() {
        return super.codeGeneration();
    }
}
