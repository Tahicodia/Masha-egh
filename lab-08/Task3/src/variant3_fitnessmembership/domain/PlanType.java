package variant3_fitnessmembership.domain;

/** Тип пакету (поки без поведінки — логіка ціни ще в MembershipPlan, див. Task4). */
public enum PlanType {
    DAY("Day"), FULL("Full"), VIP("VIP");

    private final String displayName;

    PlanType(String displayName) { this.displayName = displayName; }

    public String displayName() { return displayName; }

    public static PlanType parse(String code) {
        if (code != null) {
            for (PlanType type : values()) {
                if (type.displayName.equalsIgnoreCase(code.trim())) return type;
            }
        }
        throw new IllegalArgumentException("Невідомий тип абонемента: '" + code + "' (очікується Day/Full/VIP)");
    }
}
