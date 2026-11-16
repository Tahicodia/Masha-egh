package posylka;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/** Раннер: окремий метод для кожної проблемної ситуації. */
public class Main {

    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        System.out.println("===== Завдання 1. Відстеження статусу відправлення (Observer) =====");
        task1Observer();
        System.out.println();
        System.out.println("===== Завдання 2. Робота сортувального центру (Mediator) =====");
        task2Mediator();
    }

    static void task1Observer() {
        Shipment shipment = new Shipment("PL0001234", "прийнято");
        StatusObserver sender = new Sender("ТОВ «Ромашка»");
        StatusObserver receiver = new Receiver("Іваненко І.");
        StatusObserver accounting = new Accounting(450.00);
        StatusObserver shop = new OnlineShop("TechStore");

        Logger.log("Main", "крок 1: підписуємо відправника, отримувача, бухгалтерію; статус 'в дорозі'");
        shipment.subscribe(sender);
        shipment.subscribe(receiver);
        shipment.subscribe(accounting);
        shipment.changeStatus("в дорозі", "Склад");

        Logger.log("Main", "крок 2: підписуємо інтернет-магазин; статус 'вручено'");
        shipment.subscribe(shop);
        shipment.changeStatus("вручено", "Кур'єр");

        Logger.log("Main", "крок 3: отримувач відписується; змінюємо статус ще раз");
        shipment.unsubscribe(receiver);
        shipment.changeStatus("повернуто", "Оператор");
    }

    static void task2Mediator() {
        SortingCenter center = new SortingCenter();
        IntakeBelt belt = new IntakeBelt(center);
        Scale scale = new Scale(center);
        BarcodeScanner scanner = new BarcodeScanner(center);
        Sorter sorter = new Sorter(center);
        LoadingZone zone = new LoadingZone(center);
        center.setDevices(belt, scale, scanner, sorter, zone);

        belt.start();

        Logger.log("Main", "сценарій 1: посилка проходить стрічку, напрямок 'Хотин', машини ще немає");
        belt.feed(new Parcel("PL-001", 5.2, "Хотин"));

        Logger.log("Main", "сценарій 2: перевантаження на вагах");
        belt.feed(new Parcel("PL-002", 80.0, "Хотин"));

        Logger.log("Main", "сценарій 3: подаємо машину на 'Хотин'");
        zone.truckArrived("Хотин");
    }
}
