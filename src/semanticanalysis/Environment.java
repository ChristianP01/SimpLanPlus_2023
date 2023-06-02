package semanticanalysis;

import ast.types.FunType;
import ast.types.Type;

import java.util.HashMap;

public class Environment {
    private HashMap<String, STentry> environment;
    private int nestingLevel;
    private int offset;

    public Environment(int nestingLevel) {
        this.environment = new HashMap<String, STentry>();
        this.nestingLevel = nestingLevel;
        this.offset = 0;
    }

    protected HashMap<String, STentry> getEnvironment() {
        return this.environment;
    }

    public STentry lookup(String id) {
        return this.environment.get(id);
    }

    public void insert(String id, Type type, String label) {
        this.offset++;
        this.environment.put(id, new STentry(type, this.nestingLevel, false, this.offset, label));
    }

    public void insert(String id, Type type) {
        this.offset++;
        this.environment.put(id, new STentry(type, this.nestingLevel, false, this.offset));
    }
}