package posylka;

/** Context: позначка на мапі, зберігає лише унікальний (зовнішній) стан + посилання на flyweight. */
public class ShipmentMarker {
    private final String waybill;
    private final double lat;
    private final double lon;
    private String status;
    private final ShipmentType type;

    public ShipmentMarker(String waybill, double lat, double lon, String status, ShipmentType type) {
        this.waybill = waybill;
        this.lat = lat;
        this.lon = lon;
        this.status = status;
        this.type = type;
    }

    public ShipmentType getType() { return type; }

    public String describe() {
        return String.format("Позначка{накладна=%s, координати=(%.4f; %.4f), статус=%s, %s, id типу=@%x}",
                waybill, lat, lon, status, type, System.identityHashCode(type));
    }
}
