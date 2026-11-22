public class InTransitState extends ShipmentState {

    @Override
    public String getName() {
        return "В дорозі";
    }

    @Override
    public void redirect(Shipment shipment, String address) {
        shipment.setAddress(address);
        shipment.log("Переадресовано на: " + address);
    }

    @Override
    public void arrive(Shipment shipment) {
        shipment.setState(new AtBranchState());
    }
}