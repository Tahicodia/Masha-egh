package ua.parcelplus.warehouse;

import ua.parcelplus.common.EventLog;

import java.util.LinkedHashMap;
import java.util.Map;

/** Роль у шаблоні: Receiver. Єдиний, хто змінює стан посилок. */
public class Warehouse {
    private static final String SRC = "Warehouse";
    private final Map<String, Parcel> parcels = new LinkedHashMap<>();

    public void register(Parcel p) { parcels.put(p.getId(), p); }
    public Parcel get(String id) { return parcels.get(id); }

    public void moveToSector(String id, String sector) {
        Parcel p = get(id);
        p.setSector(sector);
        EventLog.log(SRC, "Посилку " + id + " переміщено в сектор " + sector);
    }

    public void loadToVehicle(String id, String vehicle) {
        Parcel p = get(id);
        p.setVehicle(vehicle);
        p.setSector(null);
        EventLog.log(SRC, "Посилку " + id + " завантажено в машину " + vehicle);
    }

    public void unloadToSector(String id, String sector) {
        Parcel p = get(id);
        p.setVehicle(null);
        p.setSector(sector);
        EventLog.log(SRC, "Посилку " + id + " повернуто з машини в сектор " + sector);
    }

    public void setDamaged(String id, boolean damaged) {
        get(id).setDamaged(damaged);
        EventLog.log(SRC, "Посилку " + id + (damaged ? " позначено як пошкоджену" : " більше не позначено як пошкоджену"));
    }

    public void printState() {
        EventLog.log(SRC, "Стан складу:");
        for (Parcel p : parcels.values()) System.out.println("      " + p);
    }
}
