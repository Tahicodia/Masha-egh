package posylka;

/** ConcreteObserver: відправник, реагує на кожну зміну. */
public class Sender implements StatusObserver {
    private final String name;

    public Sender(String name) { this.name = name; }

    @Override public String getName() { return "Відправник " + name; }

    @Override
    public void update(Shipment s, String oldStatus, String newStatus) {
        Logger.log(getName(), "SMS: відправлення " + s.getWaybill() + " тепер '" + newStatus + "'");
    }
}
