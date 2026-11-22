package ua.parcelplus.visitor;

/** Concrete Element: лист. */
public class Letter implements Consignment {
    private final String waybill;
    private final int weightGrams;
    private final boolean registered;

    public Letter(String waybill, int weightGrams, boolean registered) {
        this.waybill = waybill;
        this.weightGrams = weightGrams;
        this.registered = registered;
    }

    public int weightGrams() {
        return weightGrams;
    }

    public boolean registered() {
        return registered;
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
        return "Лист " + waybill + " (" + weightGrams + " г" + (registered ? ", рекомендований" : "") + ")";
    }
}
