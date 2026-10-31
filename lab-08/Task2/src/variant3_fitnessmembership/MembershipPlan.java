package variant3_fitnessmembership;

import java.util.Objects;

public final class MembershipPlan {
    private static final double VIP_SURCHARGE = 5000.0;
    private static final double DAY_COEFFICIENT = 0.8;

    private final PlanType type;
    private final String title;
    private final double baseMonthlyRate;
    private final int monthsDuration;

    private MembershipPlan(PlanType type, String title, double baseMonthlyRate, int monthsDuration) {
        this.type = type;
        this.title = title;
        this.baseMonthlyRate = baseMonthlyRate;
        this.monthsDuration = monthsDuration;
    }

    public static MembershipPlan of(PlanType type, String title, double baseMonthlyRate, int monthsDuration) {
        Objects.requireNonNull(type);
        if (baseMonthlyRate <= 0 || monthsDuration <= 0)
            throw new IllegalArgumentException("Ставка і термін мають бути додатними");
        return new MembershipPlan(type, title, baseMonthlyRate, monthsDuration);
    }

    public double calculatePrice() {
        double raw = baseMonthlyRate * monthsDuration;
        return switch (type) {
            case VIP -> raw + VIP_SURCHARGE;
            case DAY -> raw * DAY_COEFFICIENT;
            case FULL -> raw;
        };
    }

    public PlanType getType() { return type; }
    public String getTitle() { return title; }
}