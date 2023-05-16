package ast.types;

import ast.Type;

import java.util.ArrayList;

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
        return "FunType";
    }
}