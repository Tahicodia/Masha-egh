public class Main {
    public static void main() {
        task1();
        task2();
        task3();
    }

    static void task1() {
        Logger.log("Main", "Оформлення відправлення (Facade)");
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

    static void task2(){
        Logger.log("Main", "Додаткові послуги (Decorator)");

        // 1. Звичайне відправлення
        Shipment s1 = new BasicShipment("Посилка №1", 100);
        show(s1);

        // 2. Додаємо страхування, пакування і накладений платіж
        Shipment s1Extended = new CashOnDelivery(new Packaging(new Insurance(s1)));
        show(s1Extended);

        // 3. Друге відправлення з іншою комбінацією
        Shipment s2 = new SmsNotification(new WeekendDelivery(new BasicShipment("Посилка №2", 150)));
        show(s2);
    }
    static void show(Shipment s){
        Logger.log("Main", "Опис: " + s.getDescription());
        Logger.log("Main", "Підсумкова вартість: " + s.getPrice() + " грн");
    }

    static void task3(){
        Logger.log("Main", "Трекінг партнера (Proxy)");

        Clock clock = new Clock();
        PartnerTrackingService partner = new PartnerTrackingService();
        // Кабінет бачить лише інтерфейс TrackingService
        TrackingService page = new CachingTrackingProxy(partner, clock, 300);

        // 1. Перший запит, платний
        Logger.log("Онлайн-кабінет", "запитує статус UA-1");
        page.getStatus("UA-1");

        // 2. Ще 3 запити, з кешу
        for (int i = 0; i < 3; i++) {
            Logger.log("Онлайн-кабінет", "оновлює сторінку, запитує статус UA-1");
            page.getStatus("UA-1");
        }

        // 3. Спливає час (5 хв), новий платний запит
        clock.advance(301);
        Logger.log("Main", "минуло 301 с, кеш застарів");
        Logger.log("Онлайн-кабінет", "запитує статус UA-1");
        page.getStatus("UA-1");

        Logger.log("Main", "Кількість платних запитів: " + partner.getPaidRequests());
    }
}