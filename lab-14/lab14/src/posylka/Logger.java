package posylka;

/** Журнал подій у форматі [час] [джерело] подія. Час умовний. */
public final class Logger {
    private static int tick = 0;

    private Logger() { }

    public static void log(String source, String event) {
        int s = 9 * 3600 + tick * 7;
        tick++;
        System.out.printf("[%02d:%02d:%02d] [%s] %s%n", s / 3600, (s / 60) % 60, s % 60, source, event);
    }
}
