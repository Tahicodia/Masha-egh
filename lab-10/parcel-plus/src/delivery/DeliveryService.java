package delivery;

import common.Logger;
import common.Shipment;

public abstract class DeliveryService {

    // Фабричний метод: підклас вирішує, яку доставку створити
    protected abstract Delivery createDelivery();

    // Загальний процес: описаний один раз, без розгалужень
    public final void process(Shipment shipment) {
        String src = getClass().getSimpleName();
        Logger.log(src, "Оформлення доставки для " + shipment.getId());

        if (shipment.getAddress() == null || shipment.getAddress().isBlank()) {
            Logger.log(src, "Неможливо оформити доставку: не вказано адресу");
            return;
        }
        Logger.log(src, "Адресу перевірено: " + shipment.getAddress());

        Delivery delivery = createDelivery();
        double cost = delivery.calculateCost(shipment);
        int days = delivery.calculateDays(shipment);
        Logger.log(src, String.format("Спосіб: %s, вартість: %.2f грн, строк: %d дн.",
                delivery.getName(), cost, days));

        Logger.log(src, "Виконавець: " + delivery.executor());
        Logger.log(src, "Доставку зареєстровано");
    }
}