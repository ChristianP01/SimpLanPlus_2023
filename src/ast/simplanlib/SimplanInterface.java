package ast.simplanlib;

public class SimplanInterface {
    private static int numLabel = 0;
    private static int funNumLabel = 0;

    public static String newLabel() {
        return "label" + (numLabel++);
    }

    public static String newFunLabel() {
        return "function" + (funNumLabel++);
    }
}