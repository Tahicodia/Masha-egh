package variant3_fitnessmembership.application;

import variant3_fitnessmembership.domain.MembershipPlan;

public interface PlanCatalog {
    MembershipPlan planFor(String planCode);
}
