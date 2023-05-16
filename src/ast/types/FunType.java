package ast.types;

import ast.Type;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FunType extends Type {
    private ArrayList<Type> paramTypes;
    private Type returnType;

    public FunType(ArrayList<Type> paramTypes, Type returnType) {
        this.paramTypes = paramTypes;
        this.returnType = returnType;
    }

    public ArrayList<Type> getParamTypes() {
        return paramTypes;
    }

    public Type getReturnType() {
        return returnType;
    }

    @Override
    public String toString() {
        return "Function (" + this.paramTypes.stream()
                .map(t -> t.toString())
                .collect(Collectors.joining(", "))
                + ") -> " + this.returnType.toString();
    }

    @Override
    public boolean isEqual(Type A) {
        // controllo se l'altro tipo è una funzione
        if(A instanceof FunType) {
            // controllo che il numero di parametri sia lo stesso
            if(((FunType) A).getParamTypes().size() == this.paramTypes.size()) {
                // controllo che il tipo dei parametri combaci
                boolean parMatch = IntStream.range(0, this.getParamTypes().size())
                        .allMatch(i -> this.paramTypes.get(i).isEqual(((FunType) A).getParamTypes().get(i)));

                // controllo che il tipo di ritorno sia lo stesso
                boolean retMatch = this.returnType.isEqual(((FunType) A).returnType);

                return parMatch && retMatch;
            }
        }

        return false;
    }
}