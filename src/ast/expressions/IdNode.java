package ast.expressions;

import ast.Node;
import ast.types.Type;
import ast.types.ErrorType;
import ast.types.FunType;
import ast.types.VoidType;
import semanticanalysis.STentry;
import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;

import java.util.ArrayList;

public class IdNode implements Node {

    private String id;
    private STentry semanticData;
    private int nestingLevel;

    public IdNode(String id) {
        this.id = id;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable symTable, int nesting) {
        ArrayList<SemanticError> errors = new ArrayList<SemanticError>();

        // inserisco la STentry dell'id nel campo corrispondente
        this.semanticData = symTable.lookup(this.id);

        // salvo il nesting level in cui la variabile è utilizzata
        this.nestingLevel = nesting;

        // se la funzione lookup non ha ritornato nulla, allora la variabile non è stata definita
        if(this.semanticData == null) {
            errors.add(new SemanticError("Variable " + this.id + " not declared."));
        }

        return new ArrayList<SemanticError>();
    }

    @Override
    public Type typeCheck() {
        // se non sono state inserite informazioni sulla semantica durante l'analisi semantica,
        // la variabile non è stata definita e si ritorna errore
        if(this.semanticData == null) return new ErrorType();

        // se il tipo dell'identificativo è funzione o void ritorna errore
        Type idType = this.semanticData.getType();
        if(idType instanceof FunType || idType instanceof VoidType) return new ErrorType();

        return idType;
    }

    @Override
    public String codeGeneration() {
        return null;
    }

    @Override
    public String toPrint(String s) {
        return s + "Id: " + this.id + " at nesting level " + this.semanticData.getNestingLevel() + "\n";
    }
}
