package variant3_fitnessmembership;

import java.util.Objects;

public class Member {
    private static final double CARD_DISCOUNT_RATE = 0.10;

    private final int id;
    private final String name;
    private final String phone;
    private final boolean hasDiscountCard;

    public Member(int id, String name, String phone, boolean hasDiscountCard) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Ім'я обов'язкове");
        this.id = id;
        this.name = name;
        this.phone = Objects.requireNonNull(phone);
        this.hasDiscountCard = hasDiscountCard;
    }

    public double discountRate() { return hasDiscountCard ? CARD_DISCOUNT_RATE : 0.0; }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
}
