public class PartnerScaleAdapter implements Scale {
    private final PartnerScale partner;

    public PartnerScaleAdapter(PartnerScale partner) { this.partner = partner; }

    public double getWeightKg() {
        double kg = partner.readPounds() * 0.45359237;
        Logger.log("PartnerScaleAdapter", "перетворено у " + String.format("%.2f", kg) + " кг");
        return kg;
    }

    public double getLengthCm() {
        double cm = partner.readInches() * 2.54;
        Logger.log("PartnerScaleAdapter", "перетворено у " + String.format("%.2f", cm) + " см");
        return cm;
    }
}
