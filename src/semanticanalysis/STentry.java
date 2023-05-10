package semanticanalysis;

import ast.Type;

public class STentry {

    private Type type;
    private int nestingLevel;
    private int offset; // int=4, bool=1

    public STentry(Type type, int nestingLevel, int offset) {
        this.type = type;
        this.nestingLevel = nestingLevel;
        this.offset = offset;
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

    public void setType(Type type) { this.type = type; }
    public void setNestingLevel(int nestingLevel) { this.nestingLevel = nestingLevel; }
    public void setOffset(int offset) { this.offset = offset; }
}
