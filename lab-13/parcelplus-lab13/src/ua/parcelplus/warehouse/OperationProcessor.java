package ua.parcelplus.warehouse;

import ua.parcelplus.common.EventLog;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/** Роль у шаблоні: Invoker. Черга FIFO (нічого не губиться), стек для скасування, журнал. */
public class OperationProcessor {
    private static final String SRC = "OperationProcessor";

    private final BlockingQueue<WarehouseCommand> pending = new LinkedBlockingQueue<>();
    private final Deque<WarehouseCommand> history = new ArrayDeque<>();
    private final List<JournalEntry> journal = new ArrayList<>();

    /** Сканер лише ставить операцію в чергу, виконання відбувається окремо. */
    public void submit(String source, WarehouseCommand command) {
        pending.add(command);
        EventLog.log(SRC, "Від " + source + " прийнято операцію «" + command.description()
                + "»; у черзі: " + pending.size());
    }

    public boolean processNext() {
        WarehouseCommand cmd = pending.poll();
        if (cmd == null) return false;
        EventLog.log(SRC, "Виконую «" + cmd.description() + "» (команда " + cmd.getClass().getSimpleName() + ")");
        cmd.execute();
        history.push(cmd);
        journal.add(new JournalEntry(EventLog.now(), "ВИКОНАНО", cmd.description()));
        return true;
    }

    public void processAll() { while (processNext()) { /* по одній, у порядку надходження */ } }

    public boolean undoLast() {
        WarehouseCommand cmd = history.poll();
        if (cmd == null) {
            EventLog.log(SRC, "Немає операцій для скасування");
            return false;
        }
        EventLog.log(SRC, "Скасовую «" + cmd.description() + "»");
        cmd.undo();
        journal.add(new JournalEntry(EventLog.now(), "СКАСОВАНО", cmd.description()));
        return true;
    }

    public int pendingCount() { return pending.size(); }

    public void printJournal() {
        EventLog.log(SRC, "Журнал операцій складу (" + journal.size() + " записів):");
        int i = 1;
        for (JournalEntry e : journal) {
            System.out.printf("      %d. [%s] %-9s %s%n", i++, e.time(), e.action(), e.description());
        }
    }
}
