package ast.declarations;

import ast.Node;
import ast.types.Type;
import ast.types.ErrorType;
import ast.types.FunType;
import ast.types.VoidType;
import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;
import java.util.ArrayList;

public class DecFunNode implements Node {
    private FunType type;
    private String id;
    private ArrayList<Node> params;
    private ArrayList<Node> decs;
    private ArrayList<Node> stms;
    private Node exp;

    public DecFunNode(FunType type, String id, ArrayList<Node> params, ArrayList<Node> decs, ArrayList<Node> stms, Node exp) {
        this.type = type;
        this.id = id;
        this.params = params;
        this.decs = decs;
        this.stms = stms;
        this.exp = exp;
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
            for (Node param : this.params) {
                errors.addAll(param.checkSemantics(symTable, nesting + 1));
            }

            // controllo delle dichiarazioni
            for(Node dec : this.decs) {
                errors.addAll(dec.checkSemantics(symTable, nesting + 1));
            }

            // controllo degli statements
            for(Node stm : this.stms) {
                errors.addAll(stm.checkSemantics(symTable, nesting + 1));
            }

            // controllo dell'espressione (se presente)
            if(this.exp != null) {
                errors.addAll(this.exp.checkSemantics(symTable, nesting + 1));
            }


            // uscita dallo scope
            symTable.exitScope();

            // inserimento della funzione nello scope esterno
            symTable.insert(this.id, this.type);
        }
        return errors;

    }

    @Override
    public Type typeCheck() {
        for(Node dec : this.decs) {
            dec.typeCheck();
        }

        for(Node stm : this.stms) {
            stm.typeCheck();
        }

        // se l'ultima espressione non è presente, la funzione ritorna tipo void
        Type expType = this.exp != null ? this.exp.typeCheck() : new VoidType();
        if(!expType.isEqual(this.type.getReturnType())) {
            System.out.println("Function " + this.id + " should return a type " + this.type.getReturnType().toString() +
                    ", returns a type " + expType.toString() + " instead.");
            return new ErrorType();
        } else {
            return expType;
        }
    }

    @Override
    public String codeGeneration() {
        return null;
    }
}
