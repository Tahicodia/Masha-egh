public class AtBranchState extends ShipmentState {
    private static final int MAX_STORAGE_DAYS = 5;
    private int days = 0;

    @Override
    public String getName() {
        return "У відділенні";
    }

    @Override
    public void deliver(Shipment shipment) {
        shipment.setState(new DeliveredState());
    }

    @Override
    public void storeDay(Shipment shipment) {
        days++;
        shipment.log("День зберігання: " + days);
        if (days >= MAX_STORAGE_DAYS) {
            shipment.setState(new ReturnState());
        }
    }
}