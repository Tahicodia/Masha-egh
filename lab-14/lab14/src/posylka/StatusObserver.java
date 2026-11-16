package posylka;

/** Observer: отримувач сповіщень про зміну статусу відправлення. */
public interface StatusObserver {
    String getName();
    void update(Shipment shipment, String oldStatus, String newStatus);
}
