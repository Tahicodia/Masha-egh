public class CreatedState extends ShipmentState {

    @Override
    public String getName() {
        return "Створено";
    }

    @Override
    public void changeAddress(Shipment shipment, String address) {
        shipment.setAddress(address);
        shipment.log("Адресу змінено на: " + address);
    }

    @Override
    public void accept(Shipment shipment) {
        shipment.setState(new AcceptedState());
    }
}