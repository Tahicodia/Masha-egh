package ua.parcelplus.common;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/** Консольний журнал подій у форматі [час] [джерело] подія. Час умовний: кожна подія +1 с. */
public final class Log {
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static LocalTime clock = LocalTime.of(9, 0, 0);

    private Log() {
    }

    public static String now() {
        return clock.format(FMT);
    }

    public static void log(String source, String event) {
        clock = clock.plusSeconds(1);
        System.out.println("[" + clock.format(FMT) + "] [" + source + "] " + event);
    }

    public static String money(double value) {
        return String.format(Locale.US, "%.2f грн", value);
    }
}
