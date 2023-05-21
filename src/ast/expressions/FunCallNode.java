package ast.expressions;

import ast.Node;
import ast.types.Type;
import ast.types.ErrorType;
import ast.types.FunType;
import semanticanalysis.STentry;
import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FunCallNode implements Node {
    private String id;
    private ArrayList<Node> params;
    private STentry semanticData;
    private int callNesting;

    public FunCallNode(String id, ArrayList<Node> params) {
        this.id = id;
        this.params = params;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable symTable, int nesting) {
        ArrayList<SemanticError> errors = new ArrayList<>();

        // inserisco la STentry della funzione nel campo corrispondente
        this.semanticData = symTable.lookup(this.id);

        // memorizzo nel campo corrispondente il nesting level a cui la funzione è stata chiamata
        this.callNesting = nesting;

        // se la funzione non è stata definita (= no entry nella symtable) ritorna errore
        if(this.semanticData == null) {
            errors.add(new SemanticError("Function " + this.id + " not declared."));
        } else {
            // analisi semantica delle espressioni nei parametri
            for (Node param : params) {
                errors.addAll(param.checkSemantics(symTable, nesting));
            }
        }
        return errors;

    }

    @Override
    public Type typeCheck() {
        if(this.semanticData == null) {
            System.out.println("Function not declared");
            return new ErrorType();
        } else {
            Type funType = this.semanticData.getType();
            if(funType instanceof FunType) {
                ArrayList<Type> typeParams =  ((FunType) funType).getParamTypes();
                int numParam = typeParams.size();

                // controllo che la funzione sia stata chiamata con il giusto numero di parametri
                if(this.params.size() != numParam) {
                    System.out.println("Expected " + numParam + " for function "
                            + this.id + ", got " + this.params.size() + " instead.");
                    return new ErrorType();
                } else {
                    // controlla che i tipi dei parametri attuali coincidano con quelli formali
                    boolean parametersMatch = IntStream.range(0, this.params.size())
                            .allMatch(i -> {
                                if(!this.params.get(i).typeCheck().isEqual(typeParams.get(i))) {
                                    System.out.println("Called function " + this.id + " with actual parameter #" + (i + 1)
                                            + " of type " + this.params.get(i).typeCheck().toString() +
                                            ", while type " + typeParams.get(i).toString() + " is expected.");
                                    return false;
                                }

                                return true;
                            });

                    // se i tipi dei parametri combaciano, restituisce il tipo di ritorno della funzione
                    return parametersMatch ? ((FunType) this.semanticData.getType()).getReturnType() : new ErrorType();
                    }
                } else {
                System.out.println("Identifier " + this.id + " is not a function and cannot be called.");
                return new ErrorType();
            }
        }
    }

    @Override
    public String codeGeneration() {
        return "";
    }

    @Override
    public String toPrint(String s) {
        return s + "Function " + this.id + " called at nesting level " + this.callNesting + " with parameters:\n"
                + this.params.stream()
                    .map(p -> p.toPrint(s + "\t")).collect(Collectors.joining("\n"));
    }
}
