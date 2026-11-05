import java.util.ArrayList;
import java.util.List;

public class Van {
    private final String id;
    private String courier;
    private final List<String> shipments = new ArrayList<>();
    private int trips = 0;

    // Дорога підготовка: огляд, заправка, шляховий лист
    public Van(String id) {
        Logger.log("Van", "Підготовка фургона " + id + " (огляд, заправка, шляховий лист)...");
        try { Thread.sleep(1500); } catch (InterruptedException e) { }
        this.id = id;
        Logger.log("Van", "Фургон " + id + " підготовлено");
    }

    public void assign(String courier) {
        this.courier = courier;
        trips++;
    }

    public void load(String shipment) { shipments.add(shipment); }

    // Скидання стану перед наступним рейсом
    public void unload() {
        courier = null;
        shipments.clear();
    }

    public String getId() { return id; }

    public int getTrips() { return trips; }
}