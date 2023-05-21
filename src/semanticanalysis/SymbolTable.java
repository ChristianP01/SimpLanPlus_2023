package semanticanalysis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import ast.types.Type;

public class SymbolTable {
    private final ArrayList<Environment> symTable;
    private int currentNestingLevel;

    public SymbolTable() {
        this.symTable = new ArrayList<Environment>();
        this.currentNestingLevel = -1;
        this.newScope();
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

    public void newScope() {
        this.incrementCurrentNestingLevel();
        Environment scope = new Environment(currentNestingLevel);
        this.symTable.add(scope);
    }

    public void exitScope() {
        this.symTable.remove(this.currentNestingLevel);
        this.decrementCurrentNestingLevel();
    }

    public void insert(String id, Type type) {
        this.symTable.get(this.currentNestingLevel).insert(id, type);
    }

    // Check if symbol is in current env
    public boolean topLookup(String id) {
        int size = this.symTable.size();
        if(size == 0) return false;

        return this.symTable.get(size - 1).lookup(id) != null;
    }

    // Returns type of variable "id" (eventually) found.
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
}