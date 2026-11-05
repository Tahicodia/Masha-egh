package variant3_fitnessmembership.application;

import variant3_fitnessmembership.domain.Subscription;

/** Protected Variations: push/SMS/email можна підмінити, не чіпаючи ядро. */
public interface ClientNotifier {
    void notifyRegistered(Subscription subscription);
}
