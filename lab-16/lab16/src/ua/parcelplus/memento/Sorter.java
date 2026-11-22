package ua.parcelplus.memento;

import ua.parcelplus.common.Log;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/** Originator: автоматичний сортувальник із закритим внутрішнім станом. */
public class Sorter {

    public record Parcel(String waybill, String direction) {
    }

    private final String id;
    private final Map<String, Integer> binByDirection = new LinkedHashMap<>();
    private final Map<Integer, Integer> sortedPerBin = new TreeMap<>();
    private final Deque<Parcel> queue = new ArrayDeque<>();
    private int totalSorted;

    public Sorter(String id) {
        this.id = id;
    }

    public String id() {
        return id;
    }

    public void assignBin(String direction, int bin) {
        binByDirection.put(direction, bin);
        sortedPerBin.putIfAbsent(bin, 0);
        Log.log(id, "напрямок " + direction + " -> кошик " + bin);
    }

    public void enqueue(Parcel parcel) {
        queue.addLast(parcel);
        Log.log(id, "у чергу додано " + parcel.waybill() + " (" + parcel.direction() + ")");
    }

    public void sortNext() {
        Parcel parcel = queue.pollFirst();
        if (parcel == null) {
            Log.log(id, "черга порожня");
            return;
        }
        Integer bin = binByDirection.get(parcel.direction());
        if (bin == null) {
            Log.log(id, "ПОМИЛКА: для напрямку " + parcel.direction() + " немає кошика, посилка "
                    + parcel.waybill() + " відхилена");
            return;
        }
        sortedPerBin.merge(bin, 1, Integer::sum);
        totalSorted++;
        Log.log(id, "посилка " + parcel.waybill() + " -> кошик " + bin);
    }

    /** Емуляція збою живлення: увесь внутрішній стан втрачається. */
    public void powerFailure() {
        binByDirection.clear();
        sortedPerBin.clear();
        queue.clear();
        totalSorted = 0;
        Log.log(id, "!!! ЗБІЙ ЖИВЛЕННЯ: стан скинуто");
    }

    public SorterMemento save(String label) {
        Snapshot snapshot = new Snapshot(label, Log.now(), binByDirection, sortedPerBin, queue, totalSorted);
        Log.log(id, "створено знімок '" + label + "' (оброблено " + totalSorted + ", у черзі " + queue.size() + ")");
        return snapshot;
    }

    public void restore(SorterMemento memento) {
        if (!(memento instanceof Snapshot s)) {
            throw new IllegalArgumentException("Невідомий тип знімка");
        }
        binByDirection.clear();
        binByDirection.putAll(s.binByDirection);
        sortedPerBin.clear();
        sortedPerBin.putAll(s.sortedPerBin);
        queue.clear();
        queue.addAll(s.queue);
        totalSorted = s.totalSorted;
        Log.log(id, "відновлено стан зі знімка '" + s.label + "' (створений " + s.createdAt + ")");
    }

    public String describe() {
        String q = queue.stream().map(Parcel::waybill).collect(Collectors.joining(", ", "[", "]"));
        return "кошики=" + binByDirection + ", лічильники=" + sortedPerBin
                + ", оброблено=" + totalSorted + ", черга=" + q;
    }

    /** Concrete Memento: приватний, незмінний, з глибокими копіями. */
    private static final class Snapshot implements SorterMemento {
        private final String label;
        private final String createdAt;
        private final Map<String, Integer> binByDirection;
        private final Map<Integer, Integer> sortedPerBin;
        private final Deque<Parcel> queue;
        private final int totalSorted;

        private Snapshot(String label, String createdAt, Map<String, Integer> binByDirection,
                         Map<Integer, Integer> sortedPerBin, Deque<Parcel> queue, int totalSorted) {
            this.label = label;
            this.createdAt = createdAt;
            this.binByDirection = new LinkedHashMap<>(binByDirection);
            this.sortedPerBin = new TreeMap<>(sortedPerBin);
            this.queue = new ArrayDeque<>(queue);
            this.totalSorted = totalSorted;
        }

        @Override
        public String label() {
            return label;
        }

        @Override
        public String createdAt() {
            return createdAt;
        }
    }
}
