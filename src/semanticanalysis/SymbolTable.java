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
        this.currentNestingLevel = 0;
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

    // Returns STEntry of a variable having its id, if (eventually) found.
    public STentry lookup(String id) {
        Optional<Environment> maybeEnv = this.symTable.stream()
                .sorted(Collections.reverseOrder())
                .filter(e -> e.lookup(id) != null)
                .findFirst();

        // If found in an env, return it, else return null.
        return maybeEnv.map(env -> env.lookup(id)).orElse(null);
    }
}