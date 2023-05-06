package compiler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.SimpleTimeZone;
import java.util.stream.Collectors;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

public class SimpLanPlusErrorHandler extends BaseErrorListener {

    private final ArrayList<String> errorList;

    public SimpLanPlusErrorHandler() {
        errorList = new ArrayList<String>();
    }

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                            int line, int charPositionInLine, String msg,
                            RecognitionException e) {
        System.out.println("error found!");
        String errorString = "An error occurred at " + line + ":" + charPositionInLine + " " + msg;
        this.errorList.add(errorString);
    }

    @Override
    public String toString() {
        return String.join("\n", this.errorList);
    }

    public void exportToFile(String outputFile) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));
        bw.write(this.toString());
        bw.close();
    }
}
