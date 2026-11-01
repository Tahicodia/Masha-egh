package variant3_fitnessmembership.ui;

import variant3_fitnessmembership.application.RegisterSubscriptionCommand;

/** Pure Fabrication: перетворює сирий текст з консолі на DTO-команду. Без бізнес-логіки. */
public final class ConsoleCommandParser {

    public RegisterSubscriptionCommand parse(String name, String planCode, String trainerAnswer) {
        return new RegisterSubscriptionCommand(name.trim(), planCode.trim(), isYes(trainerAnswer));
    }

    private static boolean isYes(String answer) {
        String a = answer.trim().toLowerCase();
        return a.equals("y") || a.equals("yes") || a.equals("т") || a.equals("так");
    }
}
