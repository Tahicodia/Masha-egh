package ua.parcelplus.visitor;

import java.util.Locale;

/** Concrete Element: посилка. */
public class Parcel implements Consignment {
    private final String waybill;
    private final double weightKg;
    private final double volumeLiters;

    public Parcel(String waybill, double weightKg, double volumeLiters) {
        this.waybill = waybill;
        this.weightKg = weightKg;
        this.volumeLiters = volumeLiters;
    }

    public double weightKg() {
        return weightKg;
    }

    public double volumeLiters() {
        return volumeLiters;
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
        return String.format(Locale.US, "Посилка %s (%.1f кг, %.0f л)", waybill, weightKg, volumeLiters);
    }
}
