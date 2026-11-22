package ua.parcelplus.visitor;

import java.util.Locale;

/** Concrete Element: міжнародне відправлення. */
public class InternationalShipment implements Consignment {
    private final String waybill;
    private final double weightKg;
    private final String country;
    private final double declaredValue;

    public InternationalShipment(String waybill, double weightKg, String country, double declaredValue) {
        this.waybill = waybill;
        this.weightKg = weightKg;
        this.country = country;
        this.declaredValue = declaredValue;
    }

    public double weightKg() {
        return weightKg;
    }

    public String country() {
        return country;
    }

    public double declaredValue() {
        return declaredValue;
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
        return String.format(Locale.US, "Міжнародне %s (%.1f кг, %s, цінність %.0f грн)",
                waybill, weightKg, country, declaredValue);
    }
}
