package ast.declarations;

import ast.BodyNode;
import ast.Node;
import ast.Type;
import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;
import java.util.ArrayList;

public class DecFunNode implements Node {
    private Type type;
    private String id;
    private ArrayList<Node> params;
    private BodyNode funBody;

    public DecFunNode(Type type, String id, ArrayList<Node> params, BodyNode funBody) {
        this.type = type;
        this.id = id;
        this.params = params;
        this.funBody = funBody;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(SymbolTable symTable, int nesting) {
        ArrayList<SemanticError> errors = new ArrayList<>();

        // controllo che non sia già stato dichiarato un identificatore con lo stesso nome
        if(symTable.lookup(this.id) != null) {
            errors.add(new SemanticError("Identifier" + this.id + " already declared in current scope."));
        } else {
            // creazione di un nuovo scope, locale alla funzione
            symTable.newScope();

            // inserimento della funzione nello scope corrente per permettere chiamate ricorsive
            symTable.insert(this.id, this.type);

            // controllo semantica dei parametri e conseguente inserimento nella symtable
            for (Node param : params) {
                errors.addAll(param.checkSemantics(symTable, nesting + 1));
            }

            // controllo del corpo della funzione
            errors.addAll(funBody.checkSemantics(symTable, nesting + 1));
            // uscita dallo scope
            symTable.exitScope();

            // inserimento della funzione nello scope esterno
            symTable.insert(this.id, this.type);
        }
        return errors;

    }

    @Override
    public Type typeCheck() {
        return null;
    }

    @Override
    public String codeGeneration() {
        return null;
    }
}
