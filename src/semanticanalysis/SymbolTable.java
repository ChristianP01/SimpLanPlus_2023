package semanticanalysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.IntStream;

import ast.Type;
import ast.types.ErrorType;

public class SymbolTable {
    private final ArrayList<Environment> symTable;
    private int currentOffset;

    public SymbolTable() {
        this.symTable = new ArrayList<Environment>();
        this.currentOffset = 0;
    }

    public SymbolTable(ArrayList<Environment> otherST) {
        this.symTable = new ArrayList<Environment>();
        this.symTable.addAll(otherST);
    }

    public ArrayList<Environment> getSymbolTable() { return this.symTable; }

    public void newScope() {
        this.currentOffset++;
        Environment scope = new Environment(this.currentOffset);
        this.symTable.add(scope);
    }

    public void add(Environment scope) {
        this.symTable.add(scope);
    }

    public void remove(HashMap<String, STentry> hm) {
        this.symTable.remove(hm);
    }

    // Check if symbol is in current env
    public boolean top_lookup(String id) {
        return this.symTable.get(0).lookup(id) != null;
    }

    // Returns type of variable "id" (eventually) found in hm.
    public Type lookup(HashMap<String, STentry> hm, String id) {

        if (hm.containsKey(id))
                return hm.get(id).getType();
        else
            return new ErrorType();
    }
}