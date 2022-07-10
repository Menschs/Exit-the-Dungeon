package util;

public class Debugger {

    public static void debug(Object... o) {
        StringBuilder s = new StringBuilder();
        for (Object o1 : o) {
            s.append(o1).append(" ");
        }
        System.out.println(s);
    }
}
