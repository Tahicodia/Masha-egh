package variant3_fitnessmembership.domain;

/**
 * Тип пакету. Кожна константа несе свою PricingPolicy (Polymorphism) —
 * каскад if/else за рядковим кодом з Legacy-коду зник.
 */
public enum PlanType {
    DAY("Day", new DayPricing()),
    FULL("Full", new FullPricing()),
    VIP("VIP", new VipPricing());

    private final String displayName;
    private final PricingPolicy pricing;

    PlanType(String displayName, PricingPolicy pricing) {
        this.displayName = displayName;
        this.pricing = pricing;
    }

    public double priceFor(double rawCost) { return pricing.price(rawCost); }

    public String displayName() { return displayName; }

    /** Розбір коду з тексту користувача (регістр не важливий). */
    public static PlanType parse(String code) {
        if (code != null) {
            for (PlanType type : values()) {
                if (type.displayName.equalsIgnoreCase(code.trim())) return type;
            }
        }
        throw new IllegalArgumentException("Невідомий тип абонемента: '" + code + "' (очікується Day/Full/VIP)");
    }
}
