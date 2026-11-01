package variant3_fitnessmembership;

import variant3_fitnessmembership.application.SubscriptionController;
import variant3_fitnessmembership.application.SubscriptionFactory;
import variant3_fitnessmembership.application.SubscriptionRepository;
import variant3_fitnessmembership.domain.CardDiscountPolicy;
import variant3_fitnessmembership.infrastructure.ConsolePushNotifier;
import variant3_fitnessmembership.infrastructure.InMemoryMemberRepository;
import variant3_fitnessmembership.infrastructure.InMemorySubscriptionRepository;
import variant3_fitnessmembership.infrastructure.NotifyingSubscriptionRepository;
import variant3_fitnessmembership.infrastructure.StandardPlanCatalog;
import variant3_fitnessmembership.ui.ConsoleCommandParser;
import variant3_fitnessmembership.ui.ConsoleUI;
import variant3_fitnessmembership.ui.ContractFormatter;

import java.io.PrintStream;
import java.time.Clock;
import java.util.Scanner;

/** Composition root: єдине місце, де зібрано всі залежності (Protected Variations на рівні збірки). */
public class Main {
    public static void main(String[] args) {
        PrintStream out = System.out;

        SubscriptionRepository repository = new NotifyingSubscriptionRepository(
                new InMemorySubscriptionRepository(out), new ConsolePushNotifier(out));
        SubscriptionFactory factory = new SubscriptionFactory(
                new InMemoryMemberRepository(), new StandardPlanCatalog(),
                new CardDiscountPolicy(), Clock.systemDefaultZone());

        new ConsoleUI(new Scanner(System.in), out, new ConsoleCommandParser(),
                new SubscriptionController(factory, repository), new ContractFormatter()).run();
    }
}
