package ast.simplanlib;

public class SimplanInterface {
    private static int numLabel = 0;
    private static int funNumLabel = 0;
    private static String funCode="";

    public static String newLabel() {
        return "label" + (numLabel++);
    }

    public static String newFunLabel() {
        return "function" + (funNumLabel++);
    }

    public static void putCode(String c) {
        funCode += "\n" + c;
    }

    public static String getCode() {
        return funCode;
    }
}