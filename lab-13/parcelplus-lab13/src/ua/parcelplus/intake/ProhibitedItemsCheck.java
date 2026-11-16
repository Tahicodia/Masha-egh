package ua.parcelplus.intake;

import java.util.Set;

/** ConcreteHandler: заборонені предмети. */
public class ProhibitedItemsCheck extends ShipmentCheck {
    private static final Set<String> PROHIBITED = Set.of("вибухівка", "зброя", "наркотики", "бензин");

    @Override
    protected String validate(Shipment s) {
        for (String item : s.getItems()) {
            if (PROHIBITED.contains(item.toLowerCase())) return "заборонений предмет «" + item + "»";
        }
        return null;
    }
}
