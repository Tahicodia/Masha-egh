package ua.parcelplus.iterator;

import java.util.Locale;

/** Leaf: відправлення. declaredValue == 0 означає, що цінність не оголошена. */
public class Shipment implements LoadNode {
    private final String waybill;
    private final int routeStop;
    private final double declaredValue;

    public Shipment(String waybill, int routeStop, double declaredValue) {
        this.waybill = waybill;
        this.routeStop = routeStop;
        this.declaredValue = declaredValue;
    }

    public String waybill() {
        return waybill;
    }

    public int routeStop() {
        return routeStop;
    }

    public double declaredValue() {
        return declaredValue;
    }

    public boolean hasDeclaredValue() {
        return declaredValue > 0;
    }

    @Override
    public String label() {
        return waybill;
    }

    @Override
    public String toString() {
        String value = hasDeclaredValue() ? String.format(Locale.US, "%.0f грн", declaredValue) : "—";
        return waybill + " (зупинка маршруту " + routeStop + ", цінність " + value + ")";
    }
}
