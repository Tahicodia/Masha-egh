package variant3_fitnessmembership.infrastructure;
//**Зберігає абонементи в пам'яті. */
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import variant3_fitnessmembership.application.SubscriptionRepository;
import variant3_fitnessmembership.domain.Subscription;

/** Імітація БД (таблиця subscriptions). */
public final class InMemorySubscriptionRepository implements SubscriptionRepository {
    private final Map<Integer, Subscription> table = new HashMap<>();
    private final PrintStream log;

    public InMemorySubscriptionRepository(PrintStream log) { this.log = log; }

    @Override
    public Subscription save(Subscription subscription) {
        table.put(subscription.getId(), subscription);
        log.println("[База даних] Абонемент #" + subscription.getId() + " додано до таблиці subscriptions.");
        return subscription;
    }
}
