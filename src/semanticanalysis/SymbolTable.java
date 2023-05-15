package semanticanalysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.IntStream;

import ast.Type;
import ast.types.ErrorType;

public class SymbolTable {
    private final ArrayList<HashMap<String, STentry>> symTable;

    public SymbolTable() {
        this.symTable = new ArrayList<HashMap<String, STentry>>();
    }

    public SymbolTable(ArrayList<HashMap<String, STentry>> otherST) {
        this.symTable = new ArrayList<HashMap<String, STentry>>();
        this.symTable.addAll(otherST);
    }

    public ArrayList<HashMap<String, STentry>> getSymbolTable() { return this.symTable; }

    public void newScope() {
        HashMap<String, STentry> scope = new HashMap<String, STentry>();
        this.symTable.add(scope);
    }

    public void add(HashMap<String, STentry> hm) {
        this.symTable.add(hm);
    }

    public void remove(HashMap<String, STentry> hm) {
        this.symTable.remove(hm);
    }

    // Check if symbol is in current env
    public boolean top_lookup(String id) {
        return this.symTable.get(0).containsKey(id);
    }

    // Returns type of variable "id" (eventually) found in hm.
    public Type lookup(HashMap<String, STentry> hm, String id) {

        if (hm.containsKey(id))
                return hm.get(id).getType();
        else
            return new ErrorType();
    }
}