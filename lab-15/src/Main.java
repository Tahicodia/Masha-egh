import java.util.List;

public class Main{
    public static void main(){
        task1();
        task2();
        task3();
    }

    static void task1(){
        System.out.println("Task1 - вручення відправлень (Template Method)");
        new BranchDelivery().deliver(new Parcel("NP-1001", 0));
        new ParcelLockerDelivery().deliver(new Parcel("NP-1002", 0));
        new CourierDelivery().deliver(new Parcel("NP-1003", 500));
    }

    static void task2(){
        System.out.println("Task2 - Стан відправлення (State)");

        Shipment first = new Shipment("NP-2001", "Київ, відд. №5");
        first.changeAddress("Київ, відд. №12");
        first.accept();
        first.send();
        first.arrive();
        first.deliver();

        Shipment second = new Shipment("NP-2002", "Львів, відд. №3");
        second.accept();
        second.send();
        second.changeAddress("Львів, відд. №8");
        second.redirect("Львів, відд. №8");

        Shipment third = new Shipment("NP-2003", "Одеса, відд. №1");
        third.accept();
        third.send();
        third.arrive();
        for (int i = 0; i < 5; i++) {
            third.storeDay();
        }
        third.deliver();
    }

    static void task3() {
        System.out.println("Task3 - Маршрут кур'єра (Strategy)");

        List<Address> addresses = List.of(
                new Address("Хрещатик, 10", 2.0, false, 25),
                new Address("Сагайдачного, 5", 3.5, true, 5),
                new Address("Перемоги, 40", 6.0, false, 0),
                new Address("Антоновича, 12", 1.5, false, 30),
                new Address("Дегтярівська, 8", 5.0, true, 10),
                new Address("Набережна, 3", 4.0, false, 15)
        );

        Courier courier = new Courier("Кур'єр Петренко", new ShortestDistanceStrategy());
        courier.planRoute(addresses);

        courier.setStrategy(new UrgentFirstStrategy());
        courier.planRoute(addresses);

        Log.print("Логіст", "П'ятниця, Київ — обрано принцип з урахуванням заторів");
        courier.setStrategy(new TrafficAwareStrategy());
        courier.planRoute(addresses);

        courier.setStrategy(new EcoRouteStrategy());
        courier.planRoute(addresses);
    }
}