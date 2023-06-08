package ast.statements;

import ast.Node;
import ast.simplanlib.SimplanInterface;
import ast.types.Type;
import ast.types.BoolType;
import ast.types.ErrorType;
import ast.types.VoidType;
import semanticanalysis.STentry;
import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class IfStmNode implements Node {
    private Node condition;
    private ArrayList<Node> thenBranch;
    private ArrayList<Node> elseBranch; // può anche essere vuoto

    public IfStmNode(Node condition, ArrayList<Node> thenBranch, ArrayList<Node> elseBranch) {
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

        for(Node n : thenBranch) {
            errors.addAll(n.checkSemantics(symTable, nesting));
        }

        for(Node n : elseBranch) {
            errors.addAll(n.checkSemantics(copiedST, nesting));
        }

        ArrayList<String> symTableInits = symTable.searchInits();
        ArrayList<String> copiedSTInits = copiedST.searchInits();

        // Controllo se sono state inizializzate variabili solo in un ramo dell'if
        if(symTableInits.size() != copiedSTInits.size() || !symTableInits.containsAll(copiedSTInits)) {
            ArrayList<String> copySTInits = new ArrayList<>(symTableInits);

            symTableInits.removeAll(copiedSTInits);
            copiedSTInits.removeAll(copySTInits);

            // Tutte le entry che non sono state dichiarate in entrambi i branch verranno de-inizializzate
            symTableInits.forEach((varID) -> {
                System.out.println("Variabile " + varID + " definita solamente nel then branch");
                STentry entry = symTable.lookup(varID);
                entry.deinitialize();
            });

            // Tutte le entry che non sono state dichiarate in entrambi i branch verranno de-inizializzate
            copiedSTInits.forEach((varID) -> {
                System.out.println("Variabile " + varID + " definita solamente nell'else branch");
                STentry entry = symTable.lookup(varID);
                entry.deinitialize();
            });
        }

        return errors;
    }

    @Override
    public Type typeCheck() {
        Type condType = this.condition.typeCheck();
        if(!(condType instanceof BoolType)) {
            System.out.println("If condition must be a bool, got a " + condType.toString() + " instead.");
            return new ErrorType();
        }

        for(Node n : this.thenBranch) {
            if(!(n.typeCheck() instanceof VoidType)) {
                System.out.print("Then-branch of if statements must be made of statements only, found a "
                        + n.typeCheck().toString() + " instead.");
                return new ErrorType();
            }
        }

        for(Node n : this.elseBranch) {
            if (!(n.typeCheck() instanceof VoidType)) {
                System.out.print("Else-branch of if statements must be made of statements only, found a "
                        + n.typeCheck().toString() + " instead.");
                return new ErrorType();
            }
        }

        return new VoidType();
    }

    @Override
    public String codeGeneration() {
        String thenLabel = SimplanInterface.newLabel();
        String endLabel = SimplanInterface.newLabel();

        String thenStatements = "";
        for(Node st : this.thenBranch) {
            thenStatements += st.codeGeneration();
        }

        String elseStatements = "";
        for(Node st : this.elseBranch) {
            elseStatements += st.codeGeneration();
        }

        return this.condition.codeGeneration() +
                "storei T1 1 \n" +
                "beq A0 T1 " + thenLabel + "\n" +
                elseStatements +
                "b " + endLabel + " \n" +
                thenLabel + ": \n" +
                thenStatements +
                endLabel + ": \n" ;
    }

    @Override
    public String toPrint(String s) {
        String elseString = "";
        if(this.elseBranch.size() > 0) {
            elseString = s + "\t" + "Else branch:\n" +
                    this.elseBranch.stream().map(st -> st.toPrint(s + "\t")).collect(Collectors.joining("\n"));
        }
        return s + "If statement\n" +
                this.condition.toPrint(s + "\t") +
                s + "\t" + "Then branch:\n" +
                this.thenBranch.stream().map(st -> st.toPrint(s + "\t")).collect(Collectors.joining("\n")) +
                elseString;

    }
}
