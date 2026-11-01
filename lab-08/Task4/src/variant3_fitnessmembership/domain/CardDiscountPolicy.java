package variant3_fitnessmembership.domain;

/** Знижка 10% для власників дисконтної картки. */
public final class CardDiscountPolicy implements DiscountPolicy {
    private static final double CARD_RATE = 0.10;

    @Override
    public double discountFor(Member member, double baseAmount) {
        return member.hasDiscountCard() ? baseAmount * CARD_RATE : 0.0;
    }
}
