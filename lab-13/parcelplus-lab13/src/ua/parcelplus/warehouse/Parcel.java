package ua.parcelplus.warehouse;

/** Посилка на складі: або в секторі, або в машині. */
public class Parcel {
    private final String id;
    private String sector;
    private String vehicle;
    private boolean damaged;

    public Parcel(String id, String sector) { this.id = id; this.sector = sector; }

    public String getId() { return id; }
    public String getSector() { return sector; }
    public void setSector(String sector) { this.sector = sector; }
    public String getVehicle() { return vehicle; }
    public void setVehicle(String vehicle) { this.vehicle = vehicle; }
    public boolean isDamaged() { return damaged; }
    public void setDamaged(boolean damaged) { this.damaged = damaged; }

    @Override public String toString() {
        String where = vehicle != null ? "машина " + vehicle : "сектор " + sector;
        return id + " [" + where + (damaged ? ", ПОШКОДЖЕНА" : "") + "]";
    }
}
