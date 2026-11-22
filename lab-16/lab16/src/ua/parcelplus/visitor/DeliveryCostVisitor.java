package ua.parcelplus.visitor;

import ua.parcelplus.common.Log;

/** Concrete Visitor: розрахунок вартості доставки. */
public class DeliveryCostVisitor implements ShipmentVisitor {
    private static final String SRC = "Вартість";
    private static final double LETTER_BASE = 20, LETTER_PER_GRAM = 0.05, LETTER_REGISTERED_FEE = 30;
    private static final double PARCEL_BASE = 40, PARCEL_PER_KG = 12, PARCEL_PER_LITER = 0.5;
    private static final double FREIGHT_BASE = 150, FREIGHT_PER_KG = 8, FREIGHT_PER_PALLET = 100;
    private static final double INTL_BASE = 300, INTL_PER_KG = 60, INTL_INSURANCE_RATE = 0.01;

    private double total;

    public double total() {
        return total;
    }

    @Override
    public void visit(Letter l) {
        double cost = LETTER_BASE + LETTER_PER_GRAM * l.weightGrams() + (l.registered() ? LETTER_REGISTERED_FEE : 0);
        add(l, cost, "база + вага" + (l.registered() ? " + рекомендований" : ""));
    }

    @Override
    public void visit(Parcel p) {
        double cost = PARCEL_BASE + PARCEL_PER_KG * p.weightKg() + PARCEL_PER_LITER * p.volumeLiters();
        add(p, cost, "база + вага + об'єм");
    }

    @Override
    public void visit(Freight f) {
        double cost = FREIGHT_BASE + FREIGHT_PER_KG * f.weightKg() + FREIGHT_PER_PALLET * f.palletCount();
        add(f, cost, "база + вага + палети");
    }

    @Override
    public void visit(InternationalShipment s) {
        double cost = INTL_BASE + INTL_PER_KG * s.weightKg() + INTL_INSURANCE_RATE * s.declaredValue();
        add(s, cost, "база + вага + страхування 1% цінності");
    }

    private void add(Consignment c, double cost, String formula) {
        total += cost;
        Log.log(SRC, c.waybill() + ": " + Log.money(cost) + " [" + formula + "], разом " + Log.money(total));
    }
}
