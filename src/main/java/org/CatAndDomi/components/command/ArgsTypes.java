package org.CatAndDomi.components.command;

public enum ArgsTypes {
    INTEGER(Integer.class, "<INT>", "<INT>에는 정수를 입력해야 합니다."), DOUBLE(Double.class, "<DOUBLE>", "<DOUBLE>에는 숫자를 입력해야 합니다."), STRING(String.class, "<STRING>", "<STRING>에는 문자열을 적어야 합니다.");

    public final Class clz;
    public final String argname;
    public final String help;

    ArgsTypes(Class clz, String argname, String help) {
        this.clz = clz;
        this.argname = argname;
        this.help = help;
    }
}
