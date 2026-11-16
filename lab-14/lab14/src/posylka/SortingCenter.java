package posylka;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** ConcreteMediator: усі правила взаємодії пристроїв зосереджені тут. */
public class SortingCenter implements SortingMediator {
    private static final double MAX_WEIGHT_KG = 50.0;

    private IntakeBelt belt;
    private Scale scale;
    private BarcodeScanner scanner;
    private Sorter sorter;
    private LoadingZone zone;

    private final Set<String> trucksPresent = new HashSet<>();
    private final Map<String, Deque<Parcel>> waiting = new HashMap<>();

    public void setDevices(IntakeBelt belt, Scale scale, BarcodeScanner scanner, Sorter sorter, LoadingZone zone) {
        this.belt = belt;
        this.scale = scale;
        this.scanner = scanner;
        this.sorter = sorter;
        this.zone = zone;
    }

    @Override
    public void onEvent(SortingDevice sender, SortingEvent event, Parcel parcel, String detail) {
        switch (event) {
            case PARCEL_ENTERED:
                rule("посилка на стрічці -> спочатку зважування");
                scale.weigh(parcel);
                break;
            case WEIGHED:
                if (parcel.getWeightKg() > MAX_WEIGHT_KG) {
                    rule("вага " + parcel.getWeightKg() + " кг > ліміту " + MAX_WEIGHT_KG + " кг -> зупинити стрічку");
                    belt.stop("перевантаження, посилка " + parcel.getId());
                } else {
                    rule("вага в нормі -> передати на сканування");
                    scanner.scan(parcel);
                }
                break;
            case SCANNED:
                rule("напрямок відомий -> передати сортувальнику");
                sorter.route(parcel, detail);
                break;
            case ROUTED:
                if (trucksPresent.contains(detail)) {
                    rule("машина на '" + detail + "' подана -> дозволити завантаження");
                    zone.load(parcel);
                } else {
                    rule("машини на '" + detail + "' немає -> завантаження НЕ дозволено, посилку " + parcel.getId() + " відкладено");
                    waiting.computeIfAbsent(detail, k -> new ArrayDeque<>()).add(parcel);
                }
                break;
            case TRUCK_ARRIVED:
                trucksPresent.add(detail);
                Deque<Parcel> queue = waiting.getOrDefault(detail, new ArrayDeque<>());
                rule("машина на '" + detail + "' подана -> завантажити відкладені посилки (" + queue.size() + ")");
                List<Parcel> toLoad = new ArrayList<>(queue);
                queue.clear();
                for (Parcel p : toLoad) zone.load(p);
                break;
            default:
                break;
        }
    }

    private void rule(String text) {
        Logger.log("Координатор", "правило: " + text);
    }
}
