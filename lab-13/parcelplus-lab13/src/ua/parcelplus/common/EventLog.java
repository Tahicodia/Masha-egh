package ua.parcelplus.common;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/** Консольний журнал подій у форматі [час] [джерело] подія. Час умовний: кожна подія +7 с. */
public final class EventLog {
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static LocalTime clock = LocalTime.of(9, 0, 0);

    private EventLog() { }

    public static void resetClock(LocalTime start) { clock = start; }

    public static String now() { return FMT.format(clock); }

    public static void log(String source, String event) {
        clock = clock.plusSeconds(7);
        System.out.printf("[%s] [%s] %s%n", FMT.format(clock), source, event);
    }
}
