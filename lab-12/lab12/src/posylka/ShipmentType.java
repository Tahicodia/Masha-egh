package posylka;

/** Flyweight: незмінний спільний (внутрішній) стан типу відправлення. */
public final class ShipmentType {
    private final String name;
    private final String icon;
    private final String color;
    private final String label;

    ShipmentType(String name, String icon, String color, String label) {
        this.name = name;
        this.icon = icon;
        this.color = color;
        this.label = label;
    }

    public String getName()  { return name; }
    public String getIcon()  { return icon; }
    public String getColor() { return color; }
    public String getLabel() { return label; }

    @Override
    public String toString() {
        return "Тип{" + name + ", іконка=" + icon + ", колір=" + color + ", підпис=" + label + "}";
    }
}
