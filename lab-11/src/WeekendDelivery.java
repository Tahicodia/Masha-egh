public class WeekendDelivery extends ShipmentDecorator{
    public WeekendDelivery(Shipment s){
        super(s, "доставка у вихідний", 40);
    }
}
