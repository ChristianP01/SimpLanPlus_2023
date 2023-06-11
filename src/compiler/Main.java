package compiler;

import ast.Node;
import ast.SLPVisitor;
import ast.simplanlib.ExecuteVM;
import ast.SVMVisitorImpl;
import ast.simplanlib.SimplanInterface;
import ast.types.ErrorType;
import ast.types.Type;
import org.antlr.v4.runtime.*;
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

import static org.antlr.runtime.CharStream.EOF;


public class Main {
    // colori
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";

    public static void main(String[] args) throws Exception {
        String filename = "test/test.simplan";
        FileInputStream sourceFile;

        try {
            sourceFile = new FileInputStream(filename);
        } catch (Exception e) {
            System.out.println("An error occurred when opening source file.");
            System.exit(1);
            return;
        }

        // inizializzazioni di lexer, parser e visitor
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

        // Visita dell'albero concreto e ottenimento dell'ast
        System.out.println("Starting lexical analysis...");
        Node ast = visitor.visit(parser.prog());

        ArrayList<String> lexErrors = lexer.getLexErrors();;
        if (lexErrors.size() > 0) {
            System.out.println("Lexical errors occurred. Check .log file for more details.");

            // Scrittura su file degli errori lessicali
            BufferedWriter bw = new BufferedWriter(new FileWriter(filename + "_lexicalErrors.log"));
            for (String lexError : lexer.getLexErrors()) {
                bw.write(lexError);
            }
            bw.close();
            return;
        }

        System.out.println("[SLPC] Starting parsing process...");
        if (listener.getErrorList().size() > 0) {
            System.out.println(ANSI_RED + "[SLPC] Syntactic error(s) occurred (" + listener.getErrorList().size() + " in total):");
            for(String e : listener.getErrorList()) {
                System.out.println('\t' + e + ANSI_RESET);
            }
            return;
        }
        System.out.println("[SLPC] Finished parsing process. ");

        // Inizializzazione della symbol table "globale"
        SymbolTable symTable = new SymbolTable();
        ArrayList<SemanticError> semanticErrors = ast.checkSemantics(symTable, 0);
        if(semanticErrors.size() > 0) {
            System.out.println(ANSI_RED + "[SLPC] Semantic errors found (" + semanticErrors.size() + " in total):");
            for (SemanticError se : semanticErrors) {
                System.out.println("\t" + se.toString() + ANSI_RESET);
            }
            return;
        }

        System.out.println(ANSI_RED);
        Type typeCheck = ast.typeCheck();
        System.out.println(ANSI_RESET);

        if (typeCheck instanceof ErrorType) {
            System.out.println(ANSI_RED + "[SLPC] Type error(s) occurred." + ANSI_RESET);
            return;
        }
        //System.out.println(ast.toPrint(""));
        String codegen = ast.codeGeneration();
        //System.out.println(codegen);

        // Code generation
        CharStream code = CharStreams.fromString(codegen);
        // scrittura del codice su file
        BufferedWriter bw2 = new BufferedWriter(new FileWriter(filename + ".asm"));
        bw2.write(code.toString());
        bw2.close();
        System.out.println("[SLPC] Code generated! Assembling and running generated code.");

        SVMLexer lexerASM = new SVMLexer(code);
        CommonTokenStream tokensASM = new CommonTokenStream(lexerASM);
        SVMParser parserASM = new SVMParser(tokensASM);

        //parserASM.assembly();

        SVMVisitorImpl visitorSVM = new SVMVisitorImpl();
        visitorSVM.visit(parserASM.assembly());

        System.out.println("[SLPC] Starting Virtual Machine...");
        ExecuteVM vm = new ExecuteVM(visitorSVM.code);
        vm.cpu();
    }
}