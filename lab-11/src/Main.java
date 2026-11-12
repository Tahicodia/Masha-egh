public class Main {
    public static void main() {
        task1();
    }

    static void task1() {
        Logger.log("Main", "=== Завдання 1: оформлення відправлення (Facade) ===");
        ShipmentFacade facade = new ShipmentFacade();

        // 1. Відділення: одна операція
        Logger.log("Відділення", "викликає facade.register()");
        facade.register("Відділення", "Петренко", "Чернівці, вул. Головна 1", 2);

        // 2. Онлайн-кабінет: та сама операція
        Logger.log("Онлайн-кабінет", "викликає facade.register()");
        facade.register("Онлайн-кабінет", "Коваленко", "Хотин, вул. Центральна 5", 5);

        // 3. Окремий розрахунок вартості, без фасаду
        Logger.log("Main", "Окремий розрахунок вартості напряму через підсистему");
        new PriceCalculator().calculate(3);
    }
}