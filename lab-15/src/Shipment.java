public class Shipment {
    private final String number;
    private String address;
    private ShipmentState state = new CreatedState();

    public Shipment(String number, String address) {
        this.number = number;
        this.address = address;
        log("Відправлення створено, адреса: " + address);
    }

    void setState(ShipmentState newState) {
        log("Перехід: " + state.getName() + " → " + newState.getName());
        state = newState;
    }

    void setAddress(String address) {
        this.address = address;
    }

    void log(String event) {
        Log.print(number, "[" + state.getName() + "] " + event);
    }

    public void accept() { state.accept(this); }
    public void send() { state.send(this); }
    public void arrive() { state.arrive(this); }
    public void deliver() { state.deliver(this); }
    public void changeAddress(String address) { state.changeAddress(this, address); }
    public void redirect(String address) { state.redirect(this, address); }
    public void storeDay() { state.storeDay(this); }
}
