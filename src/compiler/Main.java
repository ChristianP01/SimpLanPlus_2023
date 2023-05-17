package compiler;

import ast.Node;
import ast.SLPVisitor;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import parser.SimpLanPlusLexer;
import parser.SimpLanPlusParser;
import semanticanalysis.SymbolTable;

import java.io.FileInputStream;

public class Main {
    public static void main(String[] args) throws Exception {
        String filename = "prova1.simplan";
        FileInputStream sourceFile;

        try {
            sourceFile = new FileInputStream(filename);
        } catch (Exception e) {
            System.out.println("An error occurred when opening source file.");
            System.exit(1);
            return;
        }

        ANTLRInputStream inputStream = new ANTLRInputStream(sourceFile);
        SimpLanPlusLexer lexer = new SimpLanPlusLexer(inputStream);
        CommonTokenStream tokenList = new CommonTokenStream(lexer);
        SimpLanPlusParser parser = new SimpLanPlusParser(tokenList);
        SLPVisitor visitor = new SLPVisitor();

        SimpLanPlusErrorHandler listener = new SimpLanPlusErrorHandler();
        lexer.removeErrorListeners();
        lexer.addErrorListener(listener);

        parser.removeErrorListeners();
        parser.addErrorListener(listener);

        System.out.println("Starting parsing process...");
        // chiamata al parser
        Node ast = visitor.visit(parser.prog());
        listener.exportToFile(filename + "_lexicalErrors.txt");
        System.out.println("Finished parsing process. ");

        if (listener.getErrorList().size() > 0) {
            System.out.println("Syntactic errors occurred.");
        }
        else {
            // Initialize "global" symbol table
            SymbolTable SymbolTable = new SymbolTable();
        }

    }
}