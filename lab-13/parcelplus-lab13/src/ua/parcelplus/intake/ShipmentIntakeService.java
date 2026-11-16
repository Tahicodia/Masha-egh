package ua.parcelplus.intake;

import ua.parcelplus.common.EventLog;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/** Роль у шаблоні: Client. Збирає ланцюги для внутрішніх і міжнародних відправлень під час виконання. */
public class ShipmentIntakeService {
    private static final String SRC = "ShipmentIntakeService";

    private final Map<ShipmentType, List<ShipmentCheck>> configs = new EnumMap<>(ShipmentType.class);
    private final Map<ShipmentType, ShipmentCheck> heads = new EnumMap<>(ShipmentType.class);

    public void configure(ShipmentType type, List<ShipmentCheck> checks) {
        configs.put(type, new ArrayList<>(checks));
        relink(type);
        EventLog.log(SRC, "Ланцюг " + type + " налаштовано: " + describe(type));
    }

    public void insertCheck(ShipmentType type, int position, ShipmentCheck check) {
        configs.get(type).add(position, check);
        relink(type);
        EventLog.log(SRC, "У ланцюг " + type + " додано " + check.name() + " на позицію " + position
                + ": " + describe(type));
    }

    public boolean accept(Shipment shipment) {
        ShipmentCheck head = heads.get(shipment.getType());
        EventLog.log(SRC, "Прийом відправлення " + shipment + "; перша ланка ланцюга: " + head.name());
        boolean ok = head.handle(shipment);
        EventLog.log(SRC, "Підсумок для " + shipment.getId() + ": " + (ok ? "прийнято" : "відхилено"));
        return ok;
    }

    private void relink(ShipmentType type) {
        List<ShipmentCheck> list = configs.get(type);
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setNext(i + 1 < list.size() ? list.get(i + 1) : null);
        }
        heads.put(type, list.get(0));
    }

    private String describe(ShipmentType type) {
        StringBuilder sb = new StringBuilder();
        for (ShipmentCheck c : configs.get(type)) {
            if (sb.length() > 0) sb.append(" -> ");
            sb.append(c.name());
        }
        return sb.toString();
    }
}
