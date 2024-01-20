package org.CatAndDomi.components.command;

public enum ArgsTypes {
    INTEGER(Integer.class, "<INT>"), DOUBLE(Double.class, "<DOUBLE>"), STRING(String.class, "<STRING>");

    public final Class clz;
    public final String argname;

    ArgsTypes(Class clz, String argname) {
        this.clz = clz;
        this.argname = argname;
    }
}
