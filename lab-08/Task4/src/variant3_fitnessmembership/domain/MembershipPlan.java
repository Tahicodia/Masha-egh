package variant3_fitnessmembership.domain;

import java.util.Objects;

public final class MembershipPlan {
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

    /** Information Expert: план знає ставку й термін, тип знає свою формулу (Polymorphism). */
    public double calculatePrice() {
        return type.priceFor(baseMonthlyRate * monthsDuration);
    }

    public String code() { return type.displayName(); }
    public String getTitle() { return title; }
}
