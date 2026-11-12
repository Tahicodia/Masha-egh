public class CashOnDelivery extends ShipmentDecorator{
    public CashOnDelivery(Shipment s) {
        super(s, "накладений платіж", 25);
    }
}
