package variant3_fitnessmembership.domain;

/** Full-пакет: базова вартість без змін. */
final class FullPricing implements PricingPolicy {
    @Override
    public double price(double rawCost) { return rawCost; }
}
