public class Main {
   public static void main(String[] args) {
        task1();
        task2();
        task3();
    }

    static void task1() {
    Logger.log("Main", "Завдання 1: щоденні маршрути");

    Route reference = new Route("Чернівці – Хотин – Кельменці", 1000,
            "Чернівці", "Хотин", "Кельменці");

    Route day1 = reference.copy("2026-11-06", "Агеєнко");
    Route day2 = reference.copy("2026-11-07", "Агеєнко");
    Route day3 = reference.copy("2026-11-08", "Агеєнко");

    day2.addStop("Дунаївці");
    day2.setMaxKg(500);
    day2.setCourier("Бісмілля");

    Logger.log("Main", "Еталон:   " + reference);
    Logger.log("Main", "Маршрут 1: " + day1);
    Logger.log("Main", "Маршрут 2: " + day2);
    Logger.log("Main", "Маршрут 3: " + day3);
}

    static void task2() {
        Logger.log("Main", "Завдання 2: службові фургони");
        VanPool pool = new VanPool(3);

        // 5 кур'єрів одночасно запитують фургон
        Van v1 = pool.request("Іваненко");
        Van v2 = pool.request("Петренко");
        Van v3 = pool.request("Сидоренко");
        pool.request("Коваленко");   // у черзі
        pool.request("Шевченко");    // у черзі

        v1.load("Посилка 1");
        v2.load("Посилка 2");
        v3.load("Посилка 3");

        // Фургони повертаються, їх отримують кур'єри з черги
        pool.release(v1);
        pool.release(v2);

        pool.printStats();
    }

    static void task3(){
        Logger.log("Main", "Завдання 3: нумерація накладних");

        Registrar branch1 = new Registrar("Відділення №1");
        Registrar branch2 = new Registrar("Відділення №2");
        Registrar online = new Registrar("Онлайн-кабінет");

        for (int i=0; i<3; i++){
            branch1.registerShipment();
            branch2.registerShipment();
            online.registerShipment();
        }

        Logger.log("Відділення №1", "служба нумерації: " + branch1.getServiceId());
        Logger.log("Відділення №2", "служба нумерації: " + branch2.getServiceId());
        Logger.log("Онлайн-кабінет", "служба нумерації: " + online.getServiceId());
    }
}
