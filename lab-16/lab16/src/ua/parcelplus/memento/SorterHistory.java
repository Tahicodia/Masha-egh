package ua.parcelplus.memento;

import ua.parcelplus.common.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/** Caretaker: зберігає знімки, але не може ні читати, ні змінювати їхній вміст. */
public class SorterHistory {
    private final List<SorterMemento> snapshots = new ArrayList<>();

    public void checkpoint(Sorter sorter, String label) {
        snapshots.add(sorter.save(label));
        Log.log("Історія", "збережено знімків: " + snapshots.size() + " " + labels());
    }

    public void restoreLast(Sorter sorter) {
        if (snapshots.isEmpty()) {
            throw new IllegalStateException("Немає збережених станів");
        }
        sorter.restore(snapshots.get(snapshots.size() - 1));
    }

    public void restoreAt(Sorter sorter, int index) {
        sorter.restore(snapshots.get(index));
    }

    public int size() {
        return snapshots.size();
    }

    public List<String> labels() {
        return snapshots.stream().map(SorterMemento::label).collect(Collectors.toList());
    }
}
