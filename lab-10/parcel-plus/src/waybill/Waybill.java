package waybill;

public class Waybill {
    private final String sender;
    private final String recipientName;
    private final String recipientPhone;
    private final double weightKg;
    private final String dimensions;
    private final double declaredValue;
    private final double cashOnDelivery;
    private final boolean returnDocuments;
    private final boolean packing;
    private final boolean insurance;

    // Створити можна лише через WaybillBuilder
    Waybill(WaybillBuilder b) {
        this.sender = b.sender;
        this.recipientName = b.recipientName;
        this.recipientPhone = b.recipientPhone;
        this.weightKg = b.weightKg;
        this.dimensions = b.dimensions;
        this.declaredValue = b.declaredValue;
        this.cashOnDelivery = b.cashOnDelivery;
        this.returnDocuments = b.returnDocuments;
        this.packing = b.packing;
        this.insurance = b.insurance;
    }

    public String getSender() { return sender; }
    public String getRecipientName() { return recipientName; }
    public String getRecipientPhone() { return recipientPhone; }
    public double getWeightKg() { return weightKg; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Накладна {\n");
        sb.append("  відправник: ").append(sender).append("\n");
        sb.append("  отримувач: ").append(recipientName).append(", тел. ").append(recipientPhone).append("\n");
        sb.append("  вага: ").append(weightKg).append(" кг, габарити: ").append(dimensions).append("\n");
        sb.append("  послуги: ");
        StringBuilder services = new StringBuilder();
        if (declaredValue > 0) services.append("оголошена цінність ").append(declaredValue).append(" грн; ");
        if (cashOnDelivery > 0) services.append("накладений платіж ").append(cashOnDelivery).append(" грн; ");
        if (returnDocuments) services.append("зворотна доставка документів; ");
        if (packing) services.append("пакування; ");
        if (insurance) services.append("страхування; ");
        sb.append(services.length() == 0 ? "немає" : services).append("\n}");
        return sb.toString();
    }
}