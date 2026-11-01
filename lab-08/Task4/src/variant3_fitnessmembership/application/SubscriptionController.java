package variant3_fitnessmembership.application;

/**
 * GRASP Controller (Use-Case Controller): перший об'єкт за межами UI.
 * Не містить ні консольного вводу/виводу, ні бізнес-обчислень — лише координує.
 */
public final class SubscriptionController {
    private final SubscriptionFactory factory;
    private final SubscriptionRepository repository;

    public SubscriptionController(SubscriptionFactory factory, SubscriptionRepository repository) {
        this.factory = factory;
        this.repository = repository;
    }

    public SubscriptionReceipt register(RegisterSubscriptionCommand command) {
        // створити агрегат -> зберегти -> віддати DTO (обчислення робить сам Subscription)
        return SubscriptionReceipt.from(repository.save(factory.create(command)));
    }
}
