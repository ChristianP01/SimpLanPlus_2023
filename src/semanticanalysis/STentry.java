package semanticanalysis;

import ast.types.Type;

public class STentry {

    private Type type;
    private int nestingLevel;
    private boolean initialized;
    private int offset;
    // utilizzato solo per le funzioni
    private String label;

    public STentry(Type type, int nestingLevel, boolean initialized, int offset) {
        this.type = type;
        this.nestingLevel = nestingLevel;
        this.initialized = initialized;
        this.offset = offset;
    }

    public STentry(Type type, int nestingLevel, boolean initialized, int offset, String label) {
        this.type = type;
        this.nestingLevel = nestingLevel;
        this.initialized = initialized;
        this.offset = offset;
        this.label = label;
    }

    public STentry(STentry copyEntry) {
        this.type = copyEntry.type;
        this.nestingLevel = copyEntry.nestingLevel;
        this.initialized = copyEntry.initialized;
        this.offset = copyEntry.offset;
        this.label = copyEntry.label;
    }

    public void deinitialize() {
        this.initialized = false;
    }

    public boolean isInitialized() {
        return this.initialized;
    }

    public void initialize() {
        this.initialized = true;
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
    public String getLabel() { return label; }

    public void setType(Type type) { this.type = type; }
    public void setNestingLevel(int nestingLevel) { this.nestingLevel = nestingLevel; }
    public void setOffset(int offset) { this.offset = offset; }
}