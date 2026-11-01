package variant3_fitnessmembership.domain;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/** Агрегат: створюється лише через create() одразу у валідному стані (Creator). */
public final class Subscription {

    public enum Status { DRAFT, ACTIVE }

    private final int id;
    private final Member member;
    private final MembershipPlan plan;
    private final List<AdditionalService> services;
    private final LocalDate startDate;
    private final double baseAmount;
    private final double discountAmount;
    private final double finalCost;
    private final Status status;

    private Subscription(int id, Member member, MembershipPlan plan,
                         List<AdditionalService> services, LocalDate startDate) {
        this.id = id;
        this.member = member;
        this.plan = plan;
        this.services = List.copyOf(services);
        this.startDate = startDate;
        this.baseAmount = plan.calculatePrice()
                + this.services.stream().mapToDouble(AdditionalService::getPrice).sum();
        this.discountAmount = baseAmount * member.discountRate();
        this.finalCost = baseAmount - discountAmount;
        this.status = Status.ACTIVE;
    }

    public static Subscription create(int id, Member member, MembershipPlan plan,
                                      List<AdditionalService> services, LocalDate startDate) {
        Objects.requireNonNull(member, "member");
        Objects.requireNonNull(plan, "plan");
        Objects.requireNonNull(services, "services");
        Objects.requireNonNull(startDate, "startDate");
        return new Subscription(id, member, plan, services, startDate);
    }

    public int getId() { return id; }
    public List<AdditionalService> getServices() { return services; }
    public LocalDate getStartDate() { return startDate; }
    public double getBaseAmount() { return baseAmount; }
    public double getDiscountAmount() { return discountAmount; }
    public double getFinalCost() { return finalCost; }
    public Status getStatus() { return status; }

    // Закон Деметри: зовнішній код питає найближчого сусіда, а не ходить ланцюгами.
    public String memberName() { return member.getName(); }
    public String memberPhone() { return member.getPhone(); }
    public String planCode() { return plan.code(); }
}
