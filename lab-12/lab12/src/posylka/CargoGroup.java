package posylka;

import java.util.ArrayList;
import java.util.List;

/** Composite: група, що містить будь-які CargoComponent (у т.ч. групи того самого виду). */
public abstract class CargoGroup implements CargoComponent {
    private final String name;
    private final List<CargoComponent> children = new ArrayList<>();

    protected CargoGroup(String name) { this.name = name; }

    protected abstract String kind();

    public void add(CargoComponent c) {
        children.add(c);
        Logger.log(kind() + " " + name, "додано '" + c.getName() + "'");
    }

    public void remove(CargoComponent c) { children.remove(c); }

    @Override public String getName() { return name; }

    @Override
    public double getWeight() {
        double sum = 0;
        for (CargoComponent c : children) sum += c.getWeight();
        return sum;
    }

    @Override
    public double getDeclaredValue() {
        double sum = 0;
        for (CargoComponent c : children) sum += c.getDeclaredValue();
        return sum;
    }

    @Override
    public int getShipmentCount() {
        int sum = 0;
        for (CargoComponent c : children) sum += c.getShipmentCount();
        return sum;
    }

    @Override
    public void print(String indent) {
        System.out.printf("%s%s %s [вага=%.1f кг, вартість=%.0f грн, відправлень=%d]%n",
                indent, kind(), name, getWeight(), getDeclaredValue(), getShipmentCount());
        for (CargoComponent c : children) c.print(indent + "    ");
    }
}
