package semanticanalysis;

import ast.types.FunType;
import ast.types.Type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Environment {
    private HashMap<String, STentry> environment;
    private int nestingLevel;
    private int offset;

    public Environment(int nestingLevel) {
        this.environment = new HashMap<String, STentry>();
        this.nestingLevel = nestingLevel;
        this.offset = 0;
    }

    public Environment(Environment copyEnv) {
        this.environment = new HashMap<>();

        for(Map.Entry<String, STentry> entry : copyEnv.environment.entrySet()) {
            this.environment.put(entry.getKey(), new STentry(entry.getValue()));
        }

        this.nestingLevel = copyEnv.nestingLevel;
        this.offset = copyEnv.offset;
    }

    protected HashMap<String, STentry> getEnvironment() {
        return this.environment;
    }


    public STentry lookup(String id) {
        return this.environment.get(id);
    }

    public void insert(String id, Type type, String label) {
        this.environment.put(id, new STentry(type, this.nestingLevel, false, this.offset, label));
    }

    // per permettere chiamate ricorsive
    public void insert(String id, Type type, String label, int defNestingLevel) {
        this.environment.put(id, new STentry(type, defNestingLevel, false, this.offset, label));
    }

    // Per variabili
    public void insert(String id, Type type) {
        this.offset++;
        this.environment.put(id, new STentry(type, this.nestingLevel, false, this.offset));
    }

    public void increaseOffset() {
        this.offset++;
    }
}