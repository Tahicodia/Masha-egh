package ua.parcelplus.warehouse;

/** Запис журналу операцій складу. */
public record JournalEntry(String time, String action, String description) { }
