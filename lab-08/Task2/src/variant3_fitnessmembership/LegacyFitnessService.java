package variant3_fitnessmembership;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class LegacyFitnessService {
    public void registerSubscriptionConsole(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Реєстрація абонемента фітнес-клубу ===");
        System.out.print("Введіть ім'я клієнта: ");
        String name = scanner.nextLine();
        System.out.print("Введіть тип абонемента (Day/Full/VIP): ");
        PlanType type = PlanType.valueOf(scanner.nextLine().trim().toUpperCase());

        Member member = new Member(27, name, "+380633693839", true);
        MembershipPlan plan = MembershipPlan.of(type, "Річний абонемент", 1200.0, 12);
        Subscription sub = Subscription.create(1002, member, plan, List.of(AdditionalService.PERSONAL_TRAINER), LocalDate.of(2026, 11, 1));

        String contract = "Договір #" + sub.getId() + " з " + sub.getMember().getName() + "\nТариф: " + sub.getPlan().getType()
                + "\nБазова вартість: " + sub.getBaseAmount() + " грн"
                + "\nЗнижка: " + sub.getDiscountAmount() + " грн"
                + "\nДо сплати: " + sub.getFinalCost() + " грн";
        System.out.println(contract);
        System.out.println("[База даних] Абонемент #" + sub.getId() + " додано до таблиці subscriptions.");
        System.out.println("[Push Notification] Повідомлення надіслано на пристрій клієнта " + sub.getMember().getName());
    }
}
