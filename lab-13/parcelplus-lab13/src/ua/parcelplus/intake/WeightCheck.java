package ua.parcelplus.intake;

/** ConcreteHandler: допустима вага. */
public class WeightCheck extends ShipmentCheck {
    public static final double MAX_WEIGHT_KG = 30.0;

    @Override
    protected String validate(Shipment s) {
        if (s.getWeightKg() > MAX_WEIGHT_KG) {
            return "вага " + s.getWeightKg() + " кг перевищує максимум " + MAX_WEIGHT_KG + " кг";
        }
        return null;
    }
}
