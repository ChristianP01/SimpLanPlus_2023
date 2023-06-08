package ast.expressions;

import ast.Node;
import ast.simplanlib.SimplanInterface;
import ast.types.Type;
import ast.types.BoolType;
import ast.types.ErrorType;
import semanticanalysis.STentry;
import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;

import java.util.ArrayList;

public class IfNode implements Node {
    private Node condition;
    private Node thenBranch;
    private Node elseBranch;

    public IfNode(Node condition, Node thenBranch, Node elseBranch) {
        this.condition = condition;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable symTable, int nesting) {
        ArrayList<SemanticError> errors = new ArrayList<SemanticError>();

        errors.addAll(this.condition.checkSemantics(symTable, nesting));

        // Copio la symbol table attuale e la assegno al branch else
        SymbolTable copiedST = new SymbolTable(symTable);

        errors.addAll(this.thenBranch.checkSemantics(symTable, nesting));
        errors.addAll(this.elseBranch.checkSemantics(symTable, nesting));

        ArrayList<String> symTableInits = symTable.searchInits();
        ArrayList<String> copiedSTInits = copiedST.searchInits();

        // Controllo se sono state inizializzate variabili solo in un ramo dell'if
        if(symTableInits.size() != copiedSTInits.size() || !symTableInits.containsAll(copiedSTInits)) {
            ArrayList<String> copySTInits = new ArrayList<>(symTableInits);

            symTableInits.removeAll(copiedSTInits);
            copiedSTInits.removeAll(copySTInits);

            // Tutte le entry che non sono state dichiarate in entrambi i branch verranno de-inizializzate
            symTableInits.forEach((varID) -> {
                STentry entry = symTable.lookup(varID);
                entry.deinitialize();
            });

            copiedSTInits.forEach((varID) -> {
                STentry entry = symTable.lookup(varID);
                entry.deinitialize();
            });
        }

        return errors;
    }

    @Override
    public Type typeCheck() {
        Type condType = this.condition.typeCheck();
        Type thenType = this.thenBranch.typeCheck();
        Type elseType = this.elseBranch.typeCheck();

        // controllo che la condizione sia un bool
        if(!(condType instanceof BoolType)) {
            System.out.println("If condition must be a bool, got a " + condType.toString() + " instead.");
            return new ErrorType();
        }

        // controllo che i due rami siano dello stesso tipo
        if(!thenType.isEqual(elseType)) {
            System.out.println("In if statement, both branches should have the same type, got " +
                    thenType.toString() + " and " +
                    elseType.toString() + "instead.");
            return new ErrorType();
        }

        return thenType;
    }

    @Override
    public String codeGeneration() {

        String thenLabel = SimplanInterface.newLabel();
        String endLabel = SimplanInterface.newLabel();

        return this.condition.codeGeneration() +
                "storei T1 1 \n" +
                "beq A0 T1 " + thenLabel + "\n" +
                elseBranch.codeGeneration() +
                "b " + endLabel + " \n" +
                thenLabel + ": \n" +
                thenBranch.codeGeneration() +
                endLabel + ": \n" ;
    }

    @Override
    public String toPrint(String s) {
        return s + "If\n" +
                this.condition.toPrint(s + "\t") +
                s + "\t" + "Then branch:\n" +
                this.thenBranch.toPrint(s + "\t") +
                s + "\t" + "Else branch:\n" +
                this.elseBranch.toPrint(s + "\t");
    }
}
