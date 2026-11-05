package variant3_fitnessmembership;

import variant3_fitnessmembership.application.*;
import variant3_fitnessmembership.domain.*;
import variant3_fitnessmembership.infrastructure.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.Clock;
import java.time.LocalDate;
import java.util.List;

/** Мінімальна перевірка еквівалентності з Legacy-розрахунком (без JUnit, запуск: java SmokeTest). */
public class SmokeTest {
    private static int failed = 0;

    public static void main(String[] args) {
        PrintStream sink = new PrintStream(new ByteArrayOutputStream());
        SubscriptionController controller = new SubscriptionController(
                new SubscriptionFactory(new InMemoryMemberRepository(), new StandardPlanCatalog(), Clock.systemDefaultZone()),
                new NotifyingSubscriptionRepository(new InMemorySubscriptionRepository(sink),
                        new ConsolePushNotifier(sink)));

        // Очікування обчислені за формулою Legacy-коду: 1200*12=14400; VIP +5000; Day *0.8; тренер +3000; картка -10%
        check("Full+тренер", controller.register(new RegisterSubscriptionCommand("Іван", "Full", true)), 17400.0, 1740.0, 15660.0);
        check("VIP+тренер", controller.register(new RegisterSubscriptionCommand("Іван", "vip", true)), 22400.0, 2240.0, 20160.0);
        check("Day+тренер", controller.register(new RegisterSubscriptionCommand("Іван", "DAY", true)), 14520.0, 1452.0, 13068.0);
        check("Full без тренера", controller.register(new RegisterSubscriptionCommand("Іван", "Full", false)), 14400.0, 1440.0, 12960.0);

        // клієнт без картки -> знижка 0 (DiscountPolicy)
        Subscription noCard = Subscription.create(1, new Member(1, "Оля", "+380", false),
                MembershipPlan.of(PlanType.FULL, "Річний", 1200.0, 12), List.of(), LocalDate.now());
        assertEq("Без картки: знижка", 0.0, noCard.getDiscountAmount());
        assertEq("Без картки: до сплати", 14400.0, noCard.getFinalCost());

        // невідомий тип пакету -> помилка валідації
        try {
            controller.register(new RegisterSubscriptionCommand("Іван", "Gold", false));
            fail("Gold має відхилятися");
        } catch (IllegalArgumentException expected) { pass("Невідомий пакет відхилено"); }

        // колекція послуг імутабельна
        try { noCard.getServices().add(AdditionalService.PERSONAL_TRAINER); fail("services мають бути імутабельні"); }
        catch (UnsupportedOperationException expected) { pass("Колекція послуг імутабельна"); }

        System.out.println(failed == 0 ? "ВСІ ПЕРЕВІРКИ ПРОЙДЕНО" : "ПРОВАЛЕНО: " + failed);
        System.exit(failed == 0 ? 0 : 1);
    }

    private static void check(String name, SubscriptionReceipt r, double base, double disc, double fin) {
        assertEq(name + ": base", base, r.baseAmount());
        assertEq(name + ": discount", disc, r.discountAmount());
        assertEq(name + ": final", fin, r.finalCost());
    }
    private static void assertEq(String name, double exp, double act) {
        if (Math.abs(exp - act) < 1e-6) pass(name + " = " + act); else fail(name + ": очікувалось " + exp + ", отримано " + act);
    }
    private static void pass(String m) { System.out.println("  OK   " + m); }
    private static void fail(String m) { failed++; System.out.println("  FAIL " + m); }
}
