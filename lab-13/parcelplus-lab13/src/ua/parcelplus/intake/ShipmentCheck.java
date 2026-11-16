package ua.parcelplus.intake;

import ua.parcelplus.common.EventLog;

/** Роль у шаблоні: Handler. Кожна ланка або відхиляє відправлення, або передає далі. */
public abstract class ShipmentCheck {
    private ShipmentCheck next;

    public ShipmentCheck setNext(ShipmentCheck next) {
        this.next = next;
        return next;
    }

    /** Повертає true, якщо відправлення пройшло весь ланцюг. */
    public final boolean handle(Shipment shipment) {
        EventLog.log(name(), "Отримано відправлення " + shipment);
        String reason = validate(shipment);
        if (reason != null) {
            EventLog.log(name(), "ВІДХИЛЕНО " + shipment.getId() + ": " + reason
                    + ". Подальші перевірки не виконуються");
            return false;
        }
        if (next == null) {
            EventLog.log(name(), "Перевірку пройдено; це остання ланка, відправлення " + shipment.getId() + " ПРИЙНЯТО");
            return true;
        }
        EventLog.log(name(), "Перевірку пройдено, передаю далі -> " + next.name());
        return next.handle(shipment);
    }

    /** @return null, якщо перевірку пройдено; інакше причина відмови. */
    protected abstract String validate(Shipment shipment);

    protected String name() { return getClass().getSimpleName(); }
}
