package posylka;

/** Leaf: окреме відправлення. */
public class Shipment implements CargoComponent {
    private final String name;
    private final double weight;
    private final double declaredValue;

    public Shipment(String name, double weight, double declaredValue) {
        this.name = name;
        this.weight = weight;
        this.declaredValue = declaredValue;
    }

    @Override public String getName() { return name; }
    @Override public double getWeight() { return weight; }
    @Override public double getDeclaredValue() { return declaredValue; }
    @Override public int getShipmentCount() { return 1; }

    @Override
    public void print(String indent) {
        System.out.printf("%sВідправлення %s [вага=%.1f кг, вартість=%.0f грн]%n", indent, name, weight, declaredValue);
    }
}
