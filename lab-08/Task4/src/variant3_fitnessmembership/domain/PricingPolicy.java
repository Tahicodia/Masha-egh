package variant3_fitnessmembership.domain;

/** Protected Variations: стабільний контракт для змінної частини — алгоритму ціни за типом пакету. */
public interface PricingPolicy {
    double price(double rawCost);
}
