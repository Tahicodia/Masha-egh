package variant3_fitnessmembership.domain;

/** Protected Variations: стабільний контракт для алгоритму знижки. */
public interface DiscountPolicy {
    double discountFor(Member member, double baseAmount);
}
