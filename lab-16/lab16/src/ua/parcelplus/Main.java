package ua.parcelplus;

import ua.parcelplus.common.Log;
import ua.parcelplus.iterator.Bag;
import ua.parcelplus.iterator.Pallet;
import ua.parcelplus.iterator.Shipment;
import ua.parcelplus.iterator.ShipmentIterator;
import ua.parcelplus.iterator.Van;
import ua.parcelplus.memento.Sorter;
import ua.parcelplus.memento.SorterHistory;
import ua.parcelplus.visitor.CarbonFootprintVisitor;
import ua.parcelplus.visitor.ConsignmentBatch;
import ua.parcelplus.visitor.CustomsVisitor;
import ua.parcelplus.visitor.DeliveryCostVisitor;
import ua.parcelplus.visitor.Freight;
import ua.parcelplus.visitor.InternationalShipment;
import ua.parcelplus.visitor.Letter;
import ua.parcelplus.visitor.Parcel;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/** Раннер: демонстраційні сценарії варіанта 3 «Посилка+». */
public class Main {

    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        scenarioSorterMemento();
        scenarioVanIterator();
        scenarioConsignmentVisitor();
    }

    // ---------- Завдання 1: Memento ----------
    static void scenarioSorterMemento() {
        Log.log("Runner", "===== Завдання 1: відновлення стану сортувальника (Memento) =====");
        Sorter sorter = new Sorter("SORT-1");
        SorterHistory history = new SorterHistory();

        sorter.assignBin("KYIV", 1);
        sorter.assignBin("LVIV", 2);
        sorter.assignBin("ODESA", 3);
        String[][] parcels = {{"UA001", "KYIV"}, {"UA002", "LVIV"}, {"UA003", "KYIV"}, {"UA004", "ODESA"},
                {"UA005", "LVIV"}, {"UA006", "KYIV"}, {"UA007", "ODESA"}};
        for (String[] p : parcels) {
            sorter.enqueue(new Sorter.Parcel(p[0], p[1]));
        }
        Log.log(sorter.id(), "Стан до обробки: " + sorter.describe());

        for (int i = 0; i < 3; i++) sorter.sortNext();
        history.checkpoint(sorter, "CP-1");

        for (int i = 0; i < 2; i++) sorter.sortNext();
        history.checkpoint(sorter, "CP-2");

        sorter.sortNext();
        Log.log(sorter.id(), "Стан перед збоєм: " + sorter.describe());

        sorter.powerFailure();
        Log.log(sorter.id(), "Стан після збою: " + sorter.describe());

        history.restoreLast(sorter);
        Log.log(sorter.id(), "Стан після відновлення останнього знімка: " + sorter.describe());

        history.restoreAt(sorter, 0);
        Log.log(sorter.id(), "Попередній знімок також доступний, стан: " + sorter.describe());
    }

    // ---------- Завдання 2: Iterator ----------
    static void scenarioVanIterator() {
        Log.log("Runner", "===== Завдання 2: обхід відправлень у машині (Iterator) =====");
        Van van = new Van("VAN-07");

        Pallet p1 = new Pallet("P1");
        p1.add(new Shipment("UA-1001", 3, 0));
        Bag b1 = new Bag("B1");
        b1.add(new Shipment("UA-1002", 1, 5000));
        Bag b11 = new Bag("B1.1 (мішок у мішку)");
        b11.add(new Shipment("UA-1003", 4, 1200));
        b1.add(b11);
        p1.add(b1);

        Pallet p2 = new Pallet("P2");
        p2.add(new Shipment("UA-1004", 2, 0));
        Bag b2 = new Bag("B2");
        b2.add(new Shipment("UA-1005", 5, 800));
        b2.add(new Shipment("UA-1006", 6, 0));
        p2.add(b2);

        van.addPallet(p1);
        van.addPallet(p2);

        for (String line : van.describeStructure()) {
            Log.log("Машина", line);
        }

        traverse(van.createRouteIterator());
        traverse(van.createUnloadingIterator());
        traverse(van.createDeclaredValueIterator());

        Log.log("Runner", "--- два обходи одночасно: Маршрут і Розвантаження ---");
        ShipmentIterator route = van.createRouteIterator();
        ShipmentIterator unload = van.createUnloadingIterator();
        while (route.hasNext() || unload.hasNext()) {
            if (route.hasNext()) Log.log(route.name(), route.next().toString());
            if (unload.hasNext()) Log.log(unload.name(), unload.next().toString());
        }
        Log.log("Машина", "Стан структури не змінився після обходів: "
                + van.describeStructure().size() + " рядків опису");
    }

    private static void traverse(ShipmentIterator it) {
        Log.log("Runner", "--- обхід: " + it.name() + " ---");
        int n = 0;
        while (it.hasNext()) {
            Shipment s = it.next();
            Log.log(it.name(), (++n) + ". " + s);
        }
        Log.log(it.name(), "обхід завершено, відправлень: " + n);
    }

    // ---------- Завдання 3: Visitor ----------
    static void scenarioConsignmentVisitor() {
        Log.log("Runner", "===== Завдання 3: операції над вантажем (Visitor) =====");
        ConsignmentBatch batch = new ConsignmentBatch("BATCH-1");
        batch.add(new Letter("LT-01", 50, true));
        batch.add(new Parcel("PR-02", 2.5, 20));
        batch.add(new Freight("FR-03", 400, 2));
        batch.add(new InternationalShipment("IN-04", 3.0, "Польща", 4000));

        for (String line : batch.describe()) {
            Log.log("Набір", line);
        }

        DeliveryCostVisitor cost = new DeliveryCostVisitor();
        Log.log("Runner", "--- операція: розрахунок вартості (до: " + Log.money(cost.total()) + ") ---");
        batch.accept(cost);
        Log.log("Runner", "Підсумок вартості (після): " + Log.money(cost.total()));

        CustomsVisitor customs = new CustomsVisitor();
        Log.log("Runner", "--- операція: митна інформація (до: декларацій " + customs.declarations() + ") ---");
        batch.accept(customs);
        Log.log("Runner", "Підсумок: декларацій " + customs.declarations() + ", пропущено " + customs.skipped());

        CarbonFootprintVisitor carbon = new CarbonFootprintVisitor();
        Log.log("Runner", "--- НОВА операція: вуглецевий слід (до: 0.00 кг), класи відправлень не змінювались ---");
        batch.accept(carbon);
        Log.log("Runner", String.format(java.util.Locale.US, "Підсумок вуглецевого сліду (після): %.2f кг CO2",
                carbon.totalKgCo2()));
    }
}
