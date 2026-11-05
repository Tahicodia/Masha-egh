package variant3_fitnessmembership.application;

import variant3_fitnessmembership.domain.AdditionalService;
import variant3_fitnessmembership.domain.DiscountPolicy;
import variant3_fitnessmembership.domain.Subscription;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/** Pure Fabrication + Creator: збирає вхідні дані для агрегату Subscription з команди. */
public final class SubscriptionFactory {
    private static final int FIRST_ID = 1002;

    private final MemberRepository members;
    private final PlanCatalog plans;
    private final DiscountPolicy discountPolicy;
    private final Clock clock;
    private final AtomicInteger nextId = new AtomicInteger(FIRST_ID);

    public SubscriptionFactory(MemberRepository members, PlanCatalog plans,
                               DiscountPolicy discountPolicy, Clock clock) {
        this.members = members;
        this.plans = plans;
        this.discountPolicy = discountPolicy;
        this.clock = clock;
    }

    public Subscription create(RegisterSubscriptionCommand command) {
        return Subscription.create(
                nextId.getAndIncrement(),
                members.findOrRegister(command.memberName()),
                plans.planFor(command.planCode()),
                command.personalTrainer() ? List.of(AdditionalService.PERSONAL_TRAINER) : List.of(),
                LocalDate.now(clock),
                discountPolicy);
    }
}
