package ua.parcelplus;

import ua.parcelplus.common.EventLog;
import ua.parcelplus.intake.*;
import ua.parcelplus.warehouse.*;

import java.time.LocalTime;
import java.util.List;

/** Раннер: окремий метод для кожної проблемної ситуації. */
public class Main {
    public static void main(String[] args) {
        scenarioIntakeChecks();
        System.out.println();
        scenarioWarehouseOperations();
    }

    // ---------- Завдання 1: Chain of Responsibility ----------
    static void scenarioIntakeChecks() {
        header("ЗАВДАННЯ 1. Перевірка відправлення при прийомі (Chain of Responsibility)");
        EventLog.resetClock(LocalTime.of(9, 0, 0));

        ShipmentIntakeService intake = new ShipmentIntakeService();
        intake.configure(ShipmentType.DOMESTIC, List.of(
                new WaybillCompletenessCheck(), new ProhibitedItemsCheck(),
                new WeightCheck(), new DimensionsCheck()));
        intake.configure(ShipmentType.INTERNATIONAL, List.of(
                new WaybillCompletenessCheck(), new ProhibitedItemsCheck(),
                new WeightCheck(), new DimensionsCheck(), new InternationalRequirementsCheck()));

        sub("Сценарій 1.1: коректне міжнародне відправлення проходить усі перевірки");
        intake.accept(new Shipment("PS-1001", ShipmentType.INTERNATIONAL)
                .from("Іваненко І.", "Müller, Berlin").item("книги").weight(4.5)
                .size(40, 30, 20).customs("CN22-778"));

        sub("Сценарій 1.2: відправлення 45 кг зупиняється на перевірці ваги");
        intake.accept(new Shipment("PS-1002", ShipmentType.INTERNATIONAL)
                .from("Петренко П.", "Smith, London").item("інструменти").weight(45)
                .size(60, 40, 40).customs("CN22-779"));

        sub("Додатково: внутрішнє відправлення проходить коротший ланцюг (без митної перевірки)");
        intake.accept(new Shipment("PS-1003", ShipmentType.DOMESTIC)
                .from("Коваль О.", "Шевчук М., Львів").item("одяг").weight(2).size(30, 30, 10));

        sub("Сценарій 1.3: до працюючого ланцюга додається перевірка літієвих батарей");
        Shipment battery = new Shipment("PS-1004", ShipmentType.INTERNATIONAL)
                .from("Мельник А.", "Novak, Warsaw").item("павербанк").weight(1.2)
                .size(20, 10, 5).customs("CN22-780").lithium(false);
        System.out.println("   (до додавання перевірки)");
        intake.accept(battery);
        // позиція 4: після габаритів, перед міжнародними вимогами; існуючі класи не змінюються
        intake.insertCheck(ShipmentType.INTERNATIONAL, 4, new LithiumBatteryCheck());
        System.out.println("   (після додавання перевірки)");
        intake.accept(battery);
    }

    // ---------- Завдання 2: Command ----------
    static void scenarioWarehouseOperations() {
        header("ЗАВДАННЯ 2. Операції з посилками на складі (Command)");
        EventLog.resetClock(LocalTime.of(14, 0, 0));

        Warehouse warehouse = new Warehouse();
        warehouse.register(new Parcel("P-01", "A1"));
        warehouse.register(new Parcel("P-02", "A1"));
        warehouse.register(new Parcel("P-03", "A2"));
        warehouse.register(new Parcel("P-04", "A2"));
        warehouse.register(new Parcel("P-05", "A3"));
        warehouse.printState();

        OperationProcessor processor = new OperationProcessor();

        sub("Сценарій 2.1: сканер надсилає 5 операцій поспіль -> черга -> виконання по одній");
        processor.submit("Сканер-1", new MoveParcelCommand(warehouse, "P-01", "B2"));
        processor.submit("Сканер-1", new MoveParcelCommand(warehouse, "P-02", "B3"));
        processor.submit("Сканер-2", new MarkDamagedCommand(warehouse, "P-03"));
        processor.submit("Сканер-2", new LoadParcelCommand(warehouse, "P-04", "AA1234BB"));
        processor.submit("Сканер-1", new LoadParcelCommand(warehouse, "P-01", "AA1234BB")); // помилкове сканування
        processor.processAll();
        warehouse.printState();

        sub("Сценарій 2.2: скасування помилкового завантаження P-01");
        processor.undoLast();
        warehouse.printState();

        sub("Сценарій 2.3: журнал операцій складу");
        processor.printJournal();
    }

    private static void header(String t) { System.out.println("=".repeat(78)); System.out.println(t); System.out.println("=".repeat(78)); }
    private static void sub(String t) { System.out.println(); System.out.println("--- " + t + " ---"); }
}
