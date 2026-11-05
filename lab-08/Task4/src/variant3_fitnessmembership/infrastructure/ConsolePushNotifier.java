package variant3_fitnessmembership.infrastructure;

import variant3_fitnessmembership.application.ClientNotifier;
import variant3_fitnessmembership.domain.Subscription;

import java.io.PrintStream;

/** Імітація push-сповіщення. */
public final class ConsolePushNotifier implements ClientNotifier {
    private final PrintStream out;

    public ConsolePushNotifier(PrintStream out) { this.out = out; }

    @Override
    public void notifyRegistered(Subscription subscription) {
        out.println("[Push Notification] Повідомлення надіслано на пристрій клієнта " + subscription.memberName());
    }
}
