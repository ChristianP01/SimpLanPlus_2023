package ast.declarations;

import ast.Node;
import ast.simplanlib.SimplanInterface;
import ast.types.Type;
import ast.types.ErrorType;
import ast.types.FunType;
import ast.types.VoidType;
import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class DecFunNode implements Node {
    private FunType type;
    private String id;
    private ArrayList<DecNode> params;
    private ArrayList<Node> decs;
    private ArrayList<Node> stms;
    private Node exp;
    private String fun_label;

    public DecFunNode(FunType type, String id, ArrayList<DecNode> params, ArrayList<Node> decs, ArrayList<Node> stms, Node exp) {
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
            errors.add(new SemanticError("Identifier " + this.id + " already declared."));
        } else {

            // creazione di un nuovo scope, locale alla funzione
            symTable.newScope();

            this.fun_label = SimplanInterface.newFunLabel();

            // inserimento della funzione nello scope corrente per permettere chiamate ricorsive
            symTable.insert(this.id, this.type, nesting, this.fun_label);

            // controllo semantica dei parametri e conseguente inserimento nella symtable
            for (DecNode param : this.params) {
                errors.addAll(param.checkSemantics(symTable, symTable.getCurrentNestingLevel()));

                // Inizializzo ogni parametro formale della funzione
                symTable.lookup(param.getId()).initialize();
            }

            // incremento l'offset per tener conto dello spazio occupato dal return address
            symTable.increaseOffset();

            // controllo delle dichiarazioni
            for(Node dec : this.decs) {
                errors.addAll(dec.checkSemantics(symTable, symTable.getCurrentNestingLevel()));
            }

            // controllo degli statements
            for(Node stm : this.stms) {
                errors.addAll(stm.checkSemantics(symTable, symTable.getCurrentNestingLevel()));
            }

            // controllo dell'espressione (se presente)
            if(this.exp != null) {
                errors.addAll(this.exp.checkSemantics(symTable, symTable.getCurrentNestingLevel()));
            }

            // uscita dallo scope
            symTable.exitScope();

            // inserimento della funzione nello scope esterno
            symTable.insert(this.id, this.type, nesting, this.fun_label);
        }
        return errors;
    }

    @Override
    public Type typeCheck() {

        for (Node dec : this.decs) {
            dec.typeCheck();
        }

        for (Node stm : this.stms) {
            stm.typeCheck();
        }

        Type returnType = this.exp != null ? this.exp.typeCheck() : new VoidType();

        if (!returnType.isEqual(this.type.getReturnType())) {
            System.out.println("Function " + this.id + " should return a type " + this.type.getReturnType().toString() +
                    ", returns a type " + returnType.toString() + " instead.");

            return new ErrorType();
        }

        return this.type.getReturnType();
    }

    @Override
    public String codeGeneration() {
        StringBuilder decCodes = new StringBuilder();
        for(Node dec : this.decs) {
            decCodes.append(dec.codeGeneration());
        }

        StringBuilder stmCodes = new StringBuilder();
        for(Node stm : this.stms) {
            stmCodes.append(stm.codeGeneration());
        }

        String expCode = (this.exp == null) ? "" : this.exp.codeGeneration();

        SimplanInterface.putCode(this.fun_label + ": \n" +
                "pushr RA\n" +
                decCodes +
                stmCodes +
                expCode +
                "addi SP " + this.decs.size() + "\n" +
                "popr RA\n" +
                "addi SP " + (this.params.size() + 1) + "\n" +
                "popr FP\n" +
                "move FP AL\n" +
                "subi AL 1\n" +
                "rsub RA\n");

        return "";
    }

    @Override
    public String toPrint(String s) {
        String expPrint = this.exp != null ? this.exp.toPrint(s + "\t") : "";
        return s + "Declaration of function " + this.id
                + " with return type " + this.type.getReturnType().toPrint("")
                + "and parameters:\n" + this.params.stream().map(p -> p.toPrint(s + "\t")).collect(Collectors.joining(""))
                + s + "and body:\n" + this.decs.stream().map(d -> d.toPrint(s + "\t")).collect(Collectors.joining(""))
                + this.stms.stream().map(st -> st.toPrint(s + "\t")).collect(Collectors.joining(""))
                + expPrint;
    }
}
