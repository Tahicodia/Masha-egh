public class BasicShipment implements Shipment {
    private final String description;
    private final double price;

    public BasicShipment(String description, double price){
        this.description = description;
        this.price = price;
    }

    public String getDescription(){
        return description;
    }

    public double getPrice(){
        Logger.log("BasicShipment", "базова вартість: " + price + " грн");
        return price;
    }
}
