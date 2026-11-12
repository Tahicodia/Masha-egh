public class SmsNotification extends ShipmentDecorator{
    public SmsNotification (Shipment s){
        super(s, "СМС-сповіщення", 5);
    }
}
