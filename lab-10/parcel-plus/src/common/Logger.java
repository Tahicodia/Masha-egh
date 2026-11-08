package common;

public class Logger {
    private static int tick = 0;

    public static void log(String source, String event) {
        int t = tick++;
        System.out.printf("[10:%02d:%02d] [%s] %s%n", t / 60, t % 60, source, event);
    }
}