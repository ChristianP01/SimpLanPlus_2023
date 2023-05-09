package semanticanalysis;

public class SemanticError {
    protected String errorMessage;

    public SemanticError(String errorMessage) { this.errorMessage = errorMessage; }

    public String toString() { return this.errorMessage; }

}
