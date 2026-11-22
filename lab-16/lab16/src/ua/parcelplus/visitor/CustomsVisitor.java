package ua.parcelplus.visitor;

import ua.parcelplus.common.Log;

/** Concrete Visitor: митна інформація. Внутрішні відправлення пропускаються. */
public class CustomsVisitor implements ShipmentVisitor {
    private static final String SRC = "Митниця";
    private static final double DUTY_RATE = 0.10;

    private int declarations;
    private int skipped;

    public int declarations() {
        return declarations;
    }

    public int skipped() {
        return skipped;
    }

    @Override
    public void visit(Letter l) {
        skip(l);
    }

    @Override
    public void visit(Parcel p) {
        skip(p);
    }

    @Override
    public void visit(Freight f) {
        skip(f);
    }

    @Override
    public void visit(InternationalShipment s) {
        declarations++;
        double duty = DUTY_RATE * s.declaredValue();
        Log.log(SRC, s.waybill() + ": декларація №" + declarations + ", країна " + s.country()
                + ", цінність " + Log.money(s.declaredValue()) + ", орієнтовне мито " + Log.money(duty));
    }

    private void skip(Consignment c) {
        skipped++;
        Log.log(SRC, c.waybill() + ": внутрішнє відправлення, митне оформлення не потрібне (пропуск)");
    }
}
