package semanticanalysis;

public class Environment {

    int nestingLevel;
    SymbolTable symbolTable;

    public Environment() {
        // Default nesting level
        this.nestingLevel = 0;
        this.symbolTable = new SymbolTable();
    }
}