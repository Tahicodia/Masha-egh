package posylka;

/** ConcreteObserver: отримувач, реагує на кожну зміну (push-сповіщення). */
public class Receiver implements StatusObserver {
    private final String name;

    public Receiver(String name) { this.name = name; }

    @Override public String getName() { return "Отримувач " + name; }

    @Override
    public void update(Shipment s, String oldStatus, String newStatus) {
        Logger.log(getName(), "push: ваша посилка " + s.getWaybill() + " - '" + newStatus + "'");
    }
}
