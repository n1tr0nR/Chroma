package dev.rbn.chroma.logging;

@SuppressWarnings("CallToPrintStackTrace")
public class Logger {
    private final String name;

    public Logger(String name) {
        this.name = name;
    }

    public void info(String message) {
        System.out.println(format("INFO", message));
    }

    public void info(String message, Object... params) {
        System.out.println(format("INFO", formatParams(message, params)));
    }

    public void info(String message, Throwable throwable) {
        System.out.println(format("INFO", message));
        throwable.printStackTrace();
    }

    public void info(String message, Throwable throwable, Object... params) {
        System.out.println(format("INFO", formatParams(message, params)));
        throwable.printStackTrace();
    }


    public void warn(String message) {
        System.out.println(format("WARN", message));
    }

    public void warn(String message, Object... params) {
        System.out.println(format("WARN", formatParams(message, params)));
    }

    public void warn(String message, Throwable throwable) {
        System.out.println(format("WARN", message));
        throwable.printStackTrace();
    }

    public void warn(String message, Throwable throwable, Object... params) {
        System.out.println(format("WARN", formatParams(message, params)));
        throwable.printStackTrace();
    }


    public void error(String message) {
        System.err.println(format("ERROR", message));
    }

    public void error(String message, Object... params) {
        System.err.println(format("ERROR", formatParams(message, params)));
    }

    public void error(String message, Throwable throwable) {
        System.err.println(format("ERROR", message));
        throwable.printStackTrace();
    }

    public void error(String message, Throwable throwable, Object... params) {
        System.err.println(format("ERROR", formatParams(message, params)));
        throwable.printStackTrace();
    }


    private String format(String level, String message) {
        String color = "§bold§cyan";
        if (level.equals("ERROR")) color = "§bold§red";
        if (level.equals("WARN")) color = "§bold§yellow";

        return LogColor.format(color + "[" + level + "] [" + name + "] " + message + "§end");
    }

    private String formatParams(String message, Object... params) {
        for (Object param : params) {
            message = message.replaceFirst("\\{}", String.valueOf(param));
        }

        return message;
    }
}