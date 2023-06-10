package compiler;

import ast.Node;
import ast.SLPVisitor;
import ast.simplanlib.ExecuteVM;
import ast.SVMVisitorImpl;
import ast.types.ErrorType;
import ast.types.Type;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import parser.SVMLexer;
import parser.SVMParser;
import parser.SimpLanPlusLexer;
import parser.SimpLanPlusParser;
import semanticanalysis.SemanticError;
import semanticanalysis.SymbolTable;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.ArrayList;



public class Main {
    // colori
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static void main(String[] args) throws Exception {
        String filename = "test.simplan";
        FileInputStream sourceFile;

        try {
            sourceFile = new FileInputStream(filename);
        } catch (Exception e) {
            System.out.println("An error occurred when opening source file.");
            System.exit(1);
            return;
        }

        // inizializzaziond di lexer, parser e visitor
        ANTLRInputStream inputStream = new ANTLRInputStream(sourceFile);
        SimpLanPlusLexer lexer = new SimpLanPlusLexer(inputStream);
        CommonTokenStream tokenList = new CommonTokenStream(lexer);
        SimpLanPlusParser parser = new SimpLanPlusParser(tokenList);
        SLPVisitor visitor = new SLPVisitor();

        // introduzione del listener per gli errori lessicali e sintattici
        SimpLanPlusErrorHandler listener = new SimpLanPlusErrorHandler();
        lexer.removeErrorListeners();
        lexer.addErrorListener(listener);

        parser.removeErrorListeners();
        parser.addErrorListener(listener);

        System.out.println("[SLPC] Starting parsing process...");

        // visita dell'albero concreto e ottenimento del parse tree
        Node ast = visitor.visit(parser.prog());

        // scrittura su file degli errori lessicali
        listener.exportToFile(filename + "_lexicalErrors.txt");
        System.out.println("[SLPC] Finished parsing process. ");

        if (listener.getErrorList().size() > 0) {
            System.out.println("[SLPC] Lexical error(s) occurred (" + listener.getErrorList().size() + " in total):");
            for(String e : listener.getErrorList()) {
                System.out.println(ANSI_RED + '\t' + e + ANSI_RESET);
            }
        }
        else {
            // Inizializzazione della symbol table "globale"
            SymbolTable symTable = new SymbolTable();
            ArrayList<SemanticError> semanticErrors = ast.checkSemantics(symTable, 0);
            if(semanticErrors.size() > 0) {
                System.out.println("Semantic errors found (" + semanticErrors.size() + " in total):");
                for (SemanticError se : semanticErrors) {
                    System.out.println(se.toString());
                }
            } else {
                if (ast.typeCheck() instanceof ErrorType) {
                    System.out.println("Type checking error(s) occurred.");
                } else {
                    //System.out.println(ast.toPrint(""));
                    String codegen = ast.codeGeneration();
                    //System.out.println(codegen);

                    // Code generation
                    CharStream code = CharStreams.fromString(codegen);
                    // scrittura del codice su file
                    BufferedWriter bw = new BufferedWriter(new FileWriter("test.asm"));
                    bw.write(code.toString());
                    bw.close();
                    System.out.println("Code generated! Assembling and running generated code.");

                    SVMLexer lexerASM = new SVMLexer(code);
                    CommonTokenStream tokensASM = new CommonTokenStream(lexerASM);
                    SVMParser parserASM = new SVMParser(tokensASM);

                    //parserASM.assembly();

                    SVMVisitorImpl visitorSVM = new SVMVisitorImpl();
                    visitorSVM.visit(parserASM.assembly());

                    System.out.println("Starting Virtual Machine...");
                    ExecuteVM vm = new ExecuteVM(visitorSVM.code);
                    vm.cpu();
                }
            }
        }
    }
}