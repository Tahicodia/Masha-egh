package app;

import common.Logger;
import common.Shipment;
import delivery.*;
import packaging.*;
import waybill.*;

public class Main {

    public static void main(String[] args) {
        runPackaging();
        runDelivery();
        runWaybill();
    }

    // ---------- Завдання 1: Abstract Factory ----------
    static void runPackaging() {
        System.out.println("\n=== Завдання 1: Пакування відправлень ===");
        PackingStation station = new PackingStation();
        Shipment s = new Shipment("PKG-1", 2.5, "Чернівці, вул. Головна 1");

        station.pack(s, new StandardPackagingFactory());
        station.pack(s, new FragilePackagingFactory());
        station.pack(s, new ColdPackagingFactory());

        Logger.log("Main", "Додаємо тип «негабаритне» без змін у PackingStation");
        station.pack(s, new OversizedPackagingFactory());
    }

    // ---------- Завдання 2: Factory Method ----------
    static void runDelivery() {
        System.out.println("\n=== Завдання 2: Способи доставки ===");
        Shipment s = new Shipment("PKG-2", 3.0, "Чернівці, вул. Руська 10");

        new CourierDeliveryService().process(s);
        new ParcelLockerDeliveryService().process(s);
        new PostOfficeDeliveryService().process(s);

        Logger.log("Main", "Додаємо спосіб «доставка на поверх» без змін у DeliveryService");
        new FloorDeliveryService().process(s);
    }

    // ---------- Завдання 3: Builder ----------
    static void runWaybill() {
        System.out.println("\n=== Завдання 3: Оформлення накладної ===");

        // 1. Повна накладна
        Waybill full = new WaybillBuilder()
                .sender("ТОВ «Буковина»")
                .recipient("Коваленко І.", "+380501112233")
                .weight(3.2)
                .dimensions("40x30x20 см")
                .declaredValue(5000)
                .cashOnDelivery(5000)
                .insurance()
                .build();
        Logger.log("Main", "Сформовано повну накладну:");
        System.out.println(full);

        // 2. Типова накладна через Director
        WaybillDirector director = new WaybillDirector();
        Waybill docs = director.buildDocuments(new WaybillBuilder(),
                "ТОВ «Буковина»", "Шевченко О.", "+380671234567");
        Logger.log("Main", "Сформовано типову накладну «Документи»:");
        System.out.println(docs);

        // 3. Помилка: немає телефону
        try {
            new WaybillBuilder()
                    .sender("ТОВ «Буковина»")
                    .recipient("Мельник П.", null)
                    .weight(1.0)
                    .build();
        } catch (IllegalStateException e) {
            Logger.log("WaybillBuilder", e.getMessage());
        }
    }
}