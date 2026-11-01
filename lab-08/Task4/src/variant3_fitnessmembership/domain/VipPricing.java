package variant3_fitnessmembership.domain;

/** VIP-пакет: фіксована надбавка до базової вартості. */
final class VipPricing implements PricingPolicy {
    private static final double SURCHARGE = 5000.0;

    @Override
    public double price(double rawCost) { return rawCost + SURCHARGE; }
}
