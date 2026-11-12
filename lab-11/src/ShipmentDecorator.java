public class ShipmentDecorator implements Shipment{
    private final Shipment wrapped;
    private final String name;
    private final double extra;

    protected ShipmentDecorator(Shipment wrapped, String name, double extra){
        this.wrapped = wrapped;
        this.name = name;
        this.extra = extra;
    }

    public String getDescription(){
        return wrapped.getDescription() + " + " + name;
    }

    public double getPrice(){
        Logger.log(name, "додається " + extra + " грн до вартості обгорнутого об'єкта");
        return wrapped.getPrice() + extra;
    }
}
