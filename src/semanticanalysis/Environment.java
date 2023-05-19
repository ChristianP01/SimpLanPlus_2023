package semanticanalysis;

import ast.Type;

import java.util.HashMap;

public class Environment {
    private HashMap<String, STentry> environment;
    private int nestingLevel;
    // TODO vedere offset
    private int offset;

    public Environment(int nestingLevel) {
        this.environment = new HashMap<String, STentry>();
        this.nestingLevel = nestingLevel;
        this.offset = 0;
    }

    protected HashMap<String, STentry> getEnvironment() {
        return this.environment;
    }

    public Type lookup(String id) {
        return this.environment.get(id).getType();
    }

    public void insert(String id, Type type) {
        // TODO vedere offset
        // il valore initialized è impostato a false
        this.environment.put(id, new STentry(type, this.nestingLevel, false, this.offset));
    }
}