package variant3_fitnessmembership.domain;

import java.util.Objects;

public final class Member {
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

    public int getId() { return id; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public boolean hasDiscountCard() { return hasDiscountCard; }
}
