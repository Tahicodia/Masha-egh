package posylka;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Random;

/** Раннер: окремий метод для кожної проблемної ситуації. */
public class Main {

    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        System.out.println("===== Завдання 1. Відстеження відправлень на мапі (Flyweight) =====");
        task1Flyweight();
        System.out.println();
        System.out.println("===== Завдання 2. Сповіщення клієнтів (Bridge) =====");
        task2Bridge();
        System.out.println();
        System.out.println("===== Завдання 3. Консолідація вантажу (Composite) =====");
        task3Composite();
    }

    // ---------- Завдання 1 ----------
    static void task1Flyweight() {
        String[][] defs = {
            {"звичайне", "box.png", "сірий", "Звичайне"},
            {"крихке", "egg.png", "червоний", "Крихке"},
            {"холодове", "snowflake.png", "блакитний", "Холодове"},
            {"документи", "doc.png", "жовтий", "Документи"},
            {"негабаритне", "truck.png", "помаранчевий", "Негабаритне"}
        };
        String[] statuses = {"у дорозі", "на складі", "доставляється"};
        Random rnd = new Random(42);
        int total = 20_000;
        ShipmentMarker[] map = new ShipmentMarker[total];

        Logger.log("Map", "початок формування мапи: " + total + " відправлень");
        for (int i = 0; i < total; i++) {
            String[] d = defs[rnd.nextInt(defs.length)];
            ShipmentType type = ShipmentTypeFactory.get(d[0], d[1], d[2], d[3]);
            map[i] = new ShipmentMarker(String.format("PL%07d", i + 1),
                    48.2 + rnd.nextDouble() * 2, 24.0 + rnd.nextDouble() * 3,
                    statuses[rnd.nextInt(statuses.length)], type);
        }
        Logger.log("Map", "мапу сформовано");
        Logger.log("Map", "кількість позначок: " + total);
        Logger.log("Map", "кількість об'єктів типів: " + ShipmentTypeFactory.getCreatedCount() + " (очікується 5)");

        // дві позначки одного типу
        ShipmentMarker a = map[0];
        ShipmentMarker b = null;
        for (int i = 1; i < total; i++) {
            if (map[i].getType() == a.getType()) { b = map[i]; break; }
        }
        Logger.log("Map", "позначка A: " + a.describe());
        Logger.log("Map", "позначка B: " + b.describe());
        Logger.log("Map", "A.type == B.type ? " + (a.getType() == b.getType()) + " (той самий об'єкт типу)");
    }

    // ---------- Завдання 2 ----------
    static void task2Bridge() {
        Channel sms = new SmsChannel();
        Channel email = new EmailChannel();
        Channel viber = new ViberChannel();

        new ArrivalNotification(sms, "№5, Чернівці").send("+380501112233");
        new ArrivalNotification(email, "№5, Чернівці").send("client@example.com");
        new DelayNotification(viber, 3).send("+380671112233");

        Logger.log("Main", "підключаємо новий канал Telegram (один новий клас TelegramChannel)");
        Notification payment = new PaymentNotification(new TelegramChannel(), 149.50);
        payment.send("@client_ua");

        Logger.log("Main", "перемикаємо канал того самого сповіщення на SMS під час виконання");
        payment.setChannel(sms);
        payment.send("+380501112233");
    }

    // ---------- Завдання 3 ----------
    static void task3Composite() {
        CargoFlight flight = new CargoFlight("Чернівці-Київ #101");
        Pallet p1 = new Pallet("P-1");
        Pallet p2 = new Pallet("P-2");

        Bag b1 = new Bag("B-1");
        Bag b2 = new Bag("B-2");
        Bag inner = new Bag("B-2a (вкладений)");

        b1.add(new Shipment("PL0000001", 2.5, 1200));
        b1.add(new Shipment("PL0000002", 1.0, 500));
        inner.add(new Shipment("PL0000003", 0.8, 300));
        inner.add(new Shipment("PL0000004", 4.2, 2500));
        b2.add(new Shipment("PL0000005", 3.0, 900));
        b2.add(inner); // мішок у мішку

        p1.add(b1);
        p2.add(b2);
        p2.add(new Shipment("PL0000006", 12.0, 8000));
        flight.add(p1);
        flight.add(p2);

        Logger.log("Main", "структура рейсу:");
        flight.print("");

        report(flight);
        report(p1);
    }

    /** Один і той самий виклик для будь-якого вузла дерева. */
    static void report(CargoComponent node) {
        Logger.log("Report", String.format("%s: вага=%.1f кг, вартість=%.0f грн, відправлень=%d",
                node.getName(), node.getWeight(), node.getDeclaredValue(), node.getShipmentCount()));
    }
}
