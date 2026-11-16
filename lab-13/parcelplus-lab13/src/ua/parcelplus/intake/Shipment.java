package ua.parcelplus.intake;

import java.util.ArrayList;
import java.util.List;

/** Відправлення, яке приймає оператор відділення. */
public class Shipment {
    private final String id;
    private final ShipmentType type;
    private String sender;
    private String recipient;
    private final List<String> items = new ArrayList<>();
    private double weightKg;
    private int lengthCm, widthCm, heightCm;
    private String customsDeclaration;
    private boolean hasLithiumBattery;
    private boolean lithiumCertified;

    public Shipment(String id, ShipmentType type) { this.id = id; this.type = type; }

    public Shipment from(String sender, String recipient) { this.sender = sender; this.recipient = recipient; return this; }
    public Shipment item(String item) { items.add(item); return this; }
    public Shipment weight(double kg) { this.weightKg = kg; return this; }
    public Shipment size(int l, int w, int h) { lengthCm = l; widthCm = w; heightCm = h; return this; }
    public Shipment customs(String declaration) { this.customsDeclaration = declaration; return this; }
    public Shipment lithium(boolean certified) { this.hasLithiumBattery = true; this.lithiumCertified = certified; return this; }

    public String getId() { return id; }
    public ShipmentType getType() { return type; }
    public String getSender() { return sender; }
    public String getRecipient() { return recipient; }
    public List<String> getItems() { return items; }
    public double getWeightKg() { return weightKg; }
    public int getLengthCm() { return lengthCm; }
    public int getWidthCm() { return widthCm; }
    public int getHeightCm() { return heightCm; }
    public String getCustomsDeclaration() { return customsDeclaration; }
    public boolean hasLithiumBattery() { return hasLithiumBattery; }
    public boolean isLithiumCertified() { return lithiumCertified; }

    @Override public String toString() { return id + " (" + type + ", " + weightKg + " кг)"; }
}
