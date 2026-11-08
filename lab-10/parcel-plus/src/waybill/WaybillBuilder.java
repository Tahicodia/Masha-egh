package waybill;

public class WaybillBuilder {
    String sender;
    String recipientName;
    String recipientPhone;
    double weightKg;
    String dimensions = "не вказано";
    double declaredValue;
    double cashOnDelivery;
    boolean returnDocuments;
    boolean packing;
    boolean insurance;

    public WaybillBuilder sender(String sender) { this.sender = sender; return this; }

    public WaybillBuilder recipient(String name, String phone) {
        this.recipientName = name;
        this.recipientPhone = phone;
        return this;
    }

    public WaybillBuilder weight(double kg) { this.weightKg = kg; return this; }
    public WaybillBuilder dimensions(String d) { this.dimensions = d; return this; }
    public WaybillBuilder declaredValue(double v) { this.declaredValue = v; return this; }
    public WaybillBuilder cashOnDelivery(double v) { this.cashOnDelivery = v; return this; }
    public WaybillBuilder returnDocuments() { this.returnDocuments = true; return this; }
    public WaybillBuilder packing() { this.packing = true; return this; }
    public WaybillBuilder insurance() { this.insurance = true; return this; }

    public Waybill build() {
        if (sender == null || sender.isBlank())
            throw new IllegalStateException("Неможливо сформувати накладну: не вказано відправника");
        if (recipientName == null || recipientName.isBlank())
            throw new IllegalStateException("Неможливо сформувати накладну: не вказано отримувача");
        if (recipientPhone == null || recipientPhone.isBlank())
            throw new IllegalStateException("Неможливо сформувати накладну: не вказано телефон отримувача");
        if (weightKg <= 0)
            throw new IllegalStateException("Неможливо сформувати накладну: вага має бути більшою за нуль");
        return new Waybill(this);
    }
}