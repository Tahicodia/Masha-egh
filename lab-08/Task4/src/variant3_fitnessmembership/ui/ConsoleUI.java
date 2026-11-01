package variant3_fitnessmembership.ui;

import variant3_fitnessmembership.application.SubscriptionController;

import java.io.PrintStream;
import java.util.Scanner;

/** Thin Console UI: лише читає введення, делегує та друкує результат. */
public final class ConsoleUI {
    private final Scanner in;
    private final PrintStream out;
    private final ConsoleCommandParser parser;
    private final SubscriptionController controller;
    private final ContractFormatter formatter;

    public ConsoleUI(Scanner in, PrintStream out, ConsoleCommandParser parser,
                     SubscriptionController controller, ContractFormatter formatter) {
        this.in = in;
        this.out = out;
        this.parser = parser;
        this.controller = controller;
        this.formatter = formatter;
    }

    public void run() {
        out.println("=== Реєстрація абонемента фітнес-клубу ===");
        String name = prompt("Введіть ім'я клієнта: ");
        String plan = prompt("Введіть тип абонемента (Day/Full/VIP): ");
        String trainer = prompt("Додати персонального тренера? (так/ні): ");
        try {
            out.println(formatter.format(controller.register(parser.parse(name, plan, trainer))));
        } catch (IllegalArgumentException e) {
            out.println("Помилка: " + e.getMessage());
        }
    }

    private String prompt(String message) {
        out.print(message);
        return in.nextLine();
    }
}
