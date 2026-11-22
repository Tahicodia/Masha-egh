public abstract class ShipmentState {

    public abstract String getName();

    public void accept(Shipment shipment) {
        deny(shipment, "прийняти");
    }

    public void send(Shipment shipment) {
        deny(shipment, "відправити");
    }

    public void arrive(Shipment shipment) {
        deny(shipment, "прийняти у відділенні");
    }

    public void deliver(Shipment shipment) {
        deny(shipment, "вручити");
    }

    public void changeAddress(Shipment shipment, String address) {
        deny(shipment, "змінити адресу");
    }

    public void redirect(Shipment shipment, String address) {
        deny(shipment, "переадресувати");
    }

    public void storeDay(Shipment shipment) {
        deny(shipment, "зарахувати день зберігання");
    }

    protected void deny(Shipment shipment, String operation) {
        shipment.log("Відмова: неможливо " + operation + " у стані «" + getName() + "»");
    }
}