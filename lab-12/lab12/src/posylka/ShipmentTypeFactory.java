package posylka;

import java.util.HashMap;
import java.util.Map;

/** FlyweightFactory: повертає наявний об'єкт типу або створює новий лише при першому запиті. */
public final class ShipmentTypeFactory {
    private static final Map<String, ShipmentType> POOL = new HashMap<>();
    private static int created = 0;

    private ShipmentTypeFactory() { }

    public static ShipmentType get(String name, String icon, String color, String label) {
        ShipmentType t = POOL.get(name);
        if (t == null) {
            t = new ShipmentType(name, icon, color, label);
            POOL.put(name, t);
            created++;
            Logger.log("ShipmentTypeFactory", "створено новий об'єкт типу '" + name + "' (усього типів: " + created + ")");
        }
        return t;
    }

    public static int getCreatedCount() { return created; }
}
