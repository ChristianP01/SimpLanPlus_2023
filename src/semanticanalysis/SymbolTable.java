package semanticanalysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.IntStream;

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

    // Check if symbol is in current env
    public boolean top_lookup(String id) {
        return this.symTable.get(0).containsKey(id);
    }

    // Get nesting level of given symbol, having its id
    public int lookup(String id) {
        return IntStream.range(0, this.symTable.size())
                .filter(i -> this.symTable.get(i).containsKey(id))
                .findFirst()
                .orElse(-1);
    }
}