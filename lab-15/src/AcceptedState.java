public class AcceptedState extends ShipmentState {

    @Override
    public String getName() {
        return "Прийнято";
    }

    @Override
    public void changeAddress(Shipment shipment, String address) {
        shipment.setAddress(address);
        shipment.log("Адресу змінено на: " + address);
    }

    @Override
    public void send(Shipment shipment) {
        shipment.setState(new InTransitState());
    }
}