package variant3_fitnessmembership.application;

import variant3_fitnessmembership.domain.Subscription;

/** DTO-відповідь: усе, що потрібно UI для показу договору. Імутабельний record. */
public record SubscriptionReceipt(int id, String memberName, String memberPhone, String planCode,
                                  double baseAmount, double discountAmount, double finalCost) {

    public static SubscriptionReceipt from(Subscription s) {
        return new SubscriptionReceipt(s.getId(), s.memberName(), s.memberPhone(), s.planCode(),
                s.getBaseAmount(), s.getDiscountAmount(), s.getFinalCost());
    }
}
