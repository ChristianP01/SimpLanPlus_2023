package semanticanalysis;

import ast.Type;

import java.util.HashMap;

public class Environment {
    private HashMap<String, STentry> environment;
    private int nestingLevel;
    private int offset;

    public Environment(int nestingLevel) {
        this.environment = new HashMap<String, STentry>();
        this.nestingLevel = nestingLevel;
    }

    public Type lookup(String id) {
        return this.environment.get(id).getType();
    }

    public void insert(String id, Type type) {
        this.environment.put(id, new STentry(type, this.nestingLevel, this.offset));
    }

    public int getNestingLevel() {
        return nestingLevel;
    }

    public int getOffset() {
        return offset;
    }
}