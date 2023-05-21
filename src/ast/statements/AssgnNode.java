package ast.statements;

import ast.Node;
import ast.types.FunType;
import ast.types.Type;
import ast.types.ErrorType;
import ast.types.VoidType;
import semanticanalysis.STentry;
import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;

import java.util.ArrayList;

public class AssgnNode implements Node {
    private String id;
    private Node exp;
    private STentry semanticData;

    public AssgnNode(String id, Node exp) {
        this.id = id;
        this.exp = exp;
    }


    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable symTable, int nesting) {
        // TODO modificare per aggiungere alla symbol table che la variabile è ora utilizzata
        ArrayList<SemanticError> errors = new ArrayList<SemanticError>();

        this.semanticData = symTable.lookup(this.id);
        if(this.semanticData == null) {
            errors.add(new SemanticError("Variable " + this.id + " not declared."));
        }

        errors.addAll(this.exp.checkSemantics(symTable, nesting));

        return errors;
    }

    @Override
    public Type typeCheck() {

        if(this.semanticData == null) {
            System.out.println("Variable " + this.id + " not declared.");
            return new ErrorType();
        }

        Type idType = this.semanticData.getType();

        if(idType instanceof VoidType || idType instanceof FunType) {
            System.out.println("Cannot assign a value to identifier " + this.id
                    + " of type " + idType.toString());
        }

        Type expType = this.exp.typeCheck();

        if(!idType.isEqual(expType)) {
            System.out.println("Cannot assign a value of type " + expType.toString()
                    + " to variable " + this.id + " of type " + idType.toString());
            return new ErrorType();
        }

        return new VoidType();
    }

    @Override
    public String codeGeneration() {
        return null;
    }
}
