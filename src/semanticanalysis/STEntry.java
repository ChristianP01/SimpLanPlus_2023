package semanticanalysis;

import ast.Type;

public class STEntry {

    private Type type;
    private int nestingLevel;
    private int offset;
    private String label;

    public STEntry(Type type, int nestingLevel, int offset, String label) {
        this.type = type;
        this.nestingLevel = nestingLevel;
        this.offset = offset;
        this.label = label;
    }


    public Type getType() {
        return type;
    }

    public int getNestingLevel() {
        return nestingLevel;
    }

    public int getOffset() {
        return offset;
    }

    public String getLabel() {
        return label;
    }
}
