package variant3_fitnessmembership.infrastructure;

import variant3_fitnessmembership.application.PlanCatalog;
import variant3_fitnessmembership.domain.MembershipPlan;
import variant3_fitnessmembership.domain.PlanType;

/** Каталог тарифів (у Legacy — захардкоджені 1200 грн/міс. на 12 міс.). */
public final class StandardPlanCatalog implements PlanCatalog {
    private static final String TITLE = "Річний абонемент";
    private static final double MONTHLY_RATE = 1200.0;
    private static final int MONTHS = 12;

    @Override
    public MembershipPlan planFor(String planCode) {
        return MembershipPlan.of(PlanType.parse(planCode), TITLE, MONTHLY_RATE, MONTHS);
    }
}
