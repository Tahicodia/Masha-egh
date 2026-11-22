package ua.parcelplus.visitor;

import java.util.Locale;

/** Concrete Element: вантаж на палетах. */
public class Freight implements Consignment {
    private final String waybill;
    private final double weightKg;
    private final int palletCount;

    public Freight(String waybill, double weightKg, int palletCount) {
        this.waybill = waybill;
        this.weightKg = weightKg;
        this.palletCount = palletCount;
    }

    public double weightKg() {
        return weightKg;
    }

    public int palletCount() {
        return palletCount;
    }

    @Override
    public String waybill() {
        return waybill;
    }

    @Override
    public void accept(ShipmentVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "Вантаж %s (%.0f кг, %d палет)", waybill, weightKg, palletCount);
    }
}
