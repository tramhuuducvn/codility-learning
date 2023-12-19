package org.example.util;

public class Logger {

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_WHITE = "\u001B[37m";

    public static String getLog(Object message) {
        String str = String.valueOf(message);
        return ANSI_GREEN + "@D_LOG: " + str + ANSI_RESET;
    }

    public static String getWarn(String message) {
        return ANSI_YELLOW + "@D_LOG: " + message + ANSI_RESET;
    }

    public static String getError(String message) {
        return ANSI_RED + "@D_LOG: " + message + ANSI_RESET;
    }

    public static void log(Object message) {
        System.out.println(getLog(message));
    }

    public static void warn(String message) {
        System.out.println(getWarn(message));
    }

    public static void error(String message) {
        System.out.println(getError(message));
    }

    public static void printStackTrace(Exception ex) {
        System.out.println(ANSI_PURPLE);
        ex.printStackTrace();
        System.out.println(ANSI_RESET);
    }
}
