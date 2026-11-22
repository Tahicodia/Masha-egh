package posylka;

import  java.util.ArrayList;
import java.util.List;

/** Subject / ConcreteSubject: відправлення. Не знає конкретних отримувачів, лише інтерфейс StatusObserver. */
public class Shipment {
    private final String waybill;
    private String status;
    private final List<StatusObserver> observers = new ArrayList<>();

    public Shipment(String waybill, String status) {
        this.waybill = waybill;
        this.status = status;
    }

    public String getWaybill() { return waybill; }
    public String getStatus() { return status; }

    public void subscribe(StatusObserver o) {
        if (!observers.contains(o)) {
            observers.add(o);
            Logger.log("Shipment " + waybill, "підписано: " + o.getName() + " (підписників: " + observers.size() + ")");
        }
    }

    public void unsubscribe(StatusObserver o) {
        if (observers.remove(o)) {
            Logger.log("Shipment " + waybill, "відписано: " + o.getName() + " (підписників: " + observers.size() + ")");
        }
    }

    /** Змінює статус і сповіщає всіх поточних підписників. */
    public void changeStatus(String newStatus, String initiator) {
        String old = status;
        status = newStatus;
        Logger.log("Shipment " + waybill, "ініціатор '" + initiator + "' змінив статус: '" + old + "' -> '" + newStatus + "'");
        for (StatusObserver o : new ArrayList<>(observers)) { // копія: підписники можуть змінюватися під час сповіщення
            o.update(this, old, newStatus);
        }
    }
}
