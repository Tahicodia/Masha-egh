package variant3_fitnessmembership.infrastructure;

import variant3_fitnessmembership.application.ClientNotifier;
import variant3_fitnessmembership.application.SubscriptionRepository;
import variant3_fitnessmembership.domain.Subscription;

/** Indirection (Decorator): після успішного збереження сповіщає клієнта; контролер про це не знає. */
public final class NotifyingSubscriptionRepository implements SubscriptionRepository {
    private final SubscriptionRepository delegate;
    private final ClientNotifier notifier;

    public NotifyingSubscriptionRepository(SubscriptionRepository delegate, ClientNotifier notifier) {
        this.delegate = delegate;
        this.notifier = notifier;
    }

    @Override
    public Subscription save(Subscription subscription) {
        Subscription saved = delegate.save(subscription);
        notifier.notifyRegistered(saved);
        return saved;
    }
}
