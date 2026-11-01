package variant3_fitnessmembership.application;

import variant3_fitnessmembership.domain.Subscription;

/** Indirection: контролер не знає, де й як зберігаються абонементи. */
public interface SubscriptionRepository {
    Subscription save(Subscription subscription);
}
