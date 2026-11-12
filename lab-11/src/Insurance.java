public class Insurance extends ShipmentDecorator {
    public Insurance(Shipment s){
        super(s, "страхування", 30);
    }
}
