package semanticanalysis;

import java.util.ArrayList;
import ast.types.Type;

public class SymbolTable {
    private final ArrayList<Environment> symTable;
    private int currentNestingLevel;

    public SymbolTable() {
        this.symTable = new ArrayList<Environment>();
        this.currentNestingLevel = -1;
        this.newScope();
    }

    public SymbolTable(SymbolTable copyST) {
        this.symTable = new ArrayList<>();

        for (Environment env : copyST.symTable) {
            this.symTable.add(new Environment(env));
        }

        this.currentNestingLevel = copyST.currentNestingLevel;
    }

    public int getCurrentNestingLevel() {
        return this.currentNestingLevel;
    }

    public SymbolTable(ArrayList<Environment> otherST) {
        this.symTable = new ArrayList<Environment>();
        this.symTable.addAll(otherST);
    }

    private void incrementCurrentNestingLevel() {
        this.currentNestingLevel++;
    }

    private void decrementCurrentNestingLevel() {
        this.currentNestingLevel--;
    }

    public ArrayList<Environment> getSymbolTable() { return this.symTable; }

    // Recupera l'array di variabili inizializzate
    public ArrayList<String> searchInits() {
        ArrayList<String> initVars = new ArrayList<>();

        for(Environment env : this.symTable) {
            env.getEnvironment().forEach((key, value) -> {
                if(value.isInitialized())
                    initVars.add(key);
            });
        }

        return initVars;
    }


    public void newScope() {
        this.incrementCurrentNestingLevel();
        Environment scope = new Environment(currentNestingLevel);
        this.symTable.add(scope);
    }

    public void exitScope() {
        this.symTable.remove(this.currentNestingLevel);
        this.decrementCurrentNestingLevel();
    }

    // Inserire una variabile
    public void insert(String id, Type type) {
        this.symTable.get(this.currentNestingLevel).insert(id, type);
    }

    // Inserire una funzione
    public void insert(String id, Type type, int defNestingLevel, String label) {
        this.symTable.get(this.currentNestingLevel).insert(id, type, label, defNestingLevel);
    }

    // Check if symbol is in current env
    public boolean topLookup(String id) {
        int size = this.symTable.size();
        if(size == 0) return false;

        return this.symTable.get(size - 1).lookup(id) != null;
    }

    // Returns STEntry of a variable having its id, if (eventually) found.
    public STentry lookup(String id) {
        boolean found = false;
        int i = this.symTable.size() - 1;
        STentry semanticInfo = null;
        while(!found && i >= 0) {
            semanticInfo = this.symTable.get(i).lookup(id);
            if(semanticInfo != null) {
                found = true;
            }
            i--;
        }
        return semanticInfo;

    }

    public void increaseOffset() {
        this.symTable.get(this.currentNestingLevel).increaseOffset();
    }
}