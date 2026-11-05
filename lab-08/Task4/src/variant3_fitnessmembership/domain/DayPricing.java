package variant3_fitnessmembership.domain;

/** Day-пакет: знижковий коефіцієнт до базової вартості. */
final class DayPricing implements PricingPolicy {
    private static final double COEFFICIENT = 0.8;

    @Override
    public double price(double rawCost) { return rawCost * COEFFICIENT; }
}
