public class Main {
    public static void main(){
        task1();
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
}
