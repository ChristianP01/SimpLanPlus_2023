package semanticanalysis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.IntStream;

import ast.Type;
import ast.types.ErrorType;

public class SymbolTable {
    private final ArrayList<Environment> symTable;
    private int currentNestingLevel;

    public SymbolTable() {
        this.symTable = new ArrayList<Environment>();
        this.currentNestingLevel = 0;
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
        return this.symTable.get(-1).lookup(id) != null;
    }

    // Returns type of variable "id" (eventually) found.
    public STentry lookup(String id) {
        Optional<Environment> maybeEnv = this.symTable.stream()
                .sorted(Collections.reverseOrder())
                .filter(e -> e.lookup(id) != null)
                .findFirst();

        return maybeEnv.isPresent() ? maybeEnv.get().lookup(id) : null;
    }
}