package common;

public class Shipment {
    private final String id;
    private final double weightKg;
    private final String address;

    public Shipment(String id, double weightKg, String address) {
        this.id = id;
        this.weightKg = weightKg;
        this.address = address;
    }

    public String getId() { return id; }
    public double getWeightKg() { return weightKg; }
    public String getAddress() { return address; }

    @Override
    public String toString() {
        return id + " (" + weightKg + " кг, " + address + ")";
    }
}