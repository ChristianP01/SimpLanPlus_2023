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

        Type funType = this.semanticData.getType();
        if(funType instanceof FunType) {
            ArrayList<Type> typeParams =  ((FunType) funType).getParamTypes();
            int numParam = typeParams.size();

            // Controllo se la funzione è stata chiamata con un numero diverso di parametri rispetto alla dichiarazione
            if(this.params.size() != numParam) {
                System.out.println("Expected " + numParam + " parameter(s) for function "
                        + this.id + ", got " + this.params.size() + " instead.");
                return new ErrorType();
            }
            else {
                // Se il numero di parametri è corretto, controllo il loro tipo

                boolean correctParam = true;
                for (int i = 0; i < this.params.size() && correctParam; i++) {
                    if (!this.params.get(i).typeCheck().toString().equals(typeParams.get(i).toString())) {
                            correctParam = false;
                            System.out.println("Error on parameter #" + (i+1));
                            System.out.println("Requested param is " + typeParams.get(i).toPrint("") +
                            "but " + this.params.get(i).typeCheck().toPrint("") + "was given.");
                    }
                }

                return correctParam? ((FunType) this.semanticData.getType()).getReturnType() : new ErrorType();

                }
            } else {
            System.out.println("Identifier " + this.id + " is not a function and cannot be called.");
            return new ErrorType();
        }
    }

    @Override
    public String codeGeneration() {
        String newAl = "";
        for(int i = 0; i < this.callNesting - this.semanticData.getNestingLevel(); i++) {
            newAl += "store T1 0(T1)\n";
        }

        String parametersCode = "";
        for (Node param : this.params) {
            parametersCode += param.codeGeneration();
            parametersCode += "pushr A0\n";
        }

        return "pushr FP\n" +
                "move AL T1\n" +
                newAl +
                "pushr T1\n" +
                parametersCode +
                "move SP FP\n" +
                "addi FP " + (this.params.size() + 2) + "\n" +
                "move FP AL\n" +
                "subi AL 1\n" +
                "jsub " + this.semanticData.getLabel() + "\n";
    }

    @Override
    public String toPrint(String s) {
        return s + "Function " + this.id + " called at nesting level " + this.callNesting + " with parameters:\n"
                + this.params.stream()
                    .map(p -> p.toPrint(s + "\t")).collect(Collectors.joining("\n"));
    }
}
