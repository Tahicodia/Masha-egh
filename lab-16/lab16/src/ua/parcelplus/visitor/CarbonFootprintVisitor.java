package ua.parcelplus.visitor;

import ua.parcelplus.common.Log;

import java.util.Locale;

/** Нова операція, додана окремим класом без зміни класів відправлень. */
public class CarbonFootprintVisitor implements ShipmentVisitor {
    private static final String SRC = "Вуглець";
    private static final double LETTER_KG_CO2 = 0.02;
    private static final double PARCEL_KG_CO2_PER_KG = 0.8;
    private static final double FREIGHT_KG_CO2_PER_KG = 0.25;
    private static final double INTL_KG_CO2_PER_KG = 5.5;

    private double totalKgCo2;

    public double totalKgCo2() {
        return totalKgCo2;
    }

    @Override
    public void visit(Letter l) {
        add(l, LETTER_KG_CO2);
    }

    @Override
    public void visit(Parcel p) {
        add(p, PARCEL_KG_CO2_PER_KG * p.weightKg());
    }

    @Override
    public void visit(Freight f) {
        add(f, FREIGHT_KG_CO2_PER_KG * f.weightKg());
    }

    @Override
    public void visit(InternationalShipment s) {
        add(s, INTL_KG_CO2_PER_KG * s.weightKg());
    }

    private void add(Consignment c, double kg) {
        totalKgCo2 += kg;
        Log.log(SRC, String.format(Locale.US, "%s: %.2f кг CO2, разом %.2f кг", c.waybill(), kg, totalKgCo2));
    }
}
