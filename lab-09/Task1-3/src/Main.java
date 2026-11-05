public class Main {
    public static void main(){
        task1();
        task2();
    }

    static void task1() {
        Logger.log("Main", "Завдання 1");

        // 1. Еталон (дорога побудова)
        Route reference = new Route("Чернівці – Кам'янець – Вінниця", 1000,
                "Чернівці", "Кам'янець", "Вінниця");

        // 2. Три маршрути на 3 дні без повторної побудови
        Route day1 = reference.copy("2026-11-06", "Агеєнко");
        Route day2 = reference.copy("2026-11-07", "Агеєнко");
        Route day3 = reference.copy("2026-11-08", "Агеєнко");

        // 3. Зміни в одному маршруті
        day2.addStop("Дунаївці");
        day2.setMaxKg(500);
        day2.setCourier("Бісмілля");

        // 4. Еталон і змінений маршрут поруч
        Logger.log("Main", "Еталон:   " + reference);
        Logger.log("Main", "Маршрут 2: " + day2);
    }

    static void task2() {
        Logger.log("Main", "=== Завдання 2: службові фургони ===");
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
}
