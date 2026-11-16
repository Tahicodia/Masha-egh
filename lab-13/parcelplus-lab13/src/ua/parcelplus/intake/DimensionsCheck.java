package ua.parcelplus.intake;

/** ConcreteHandler: габарити. */
public class DimensionsCheck extends ShipmentCheck {
    public static final int MAX_SIDE_CM = 120;

    @Override
    protected String validate(Shipment s) {
        int max = Math.max(s.getLengthCm(), Math.max(s.getWidthCm(), s.getHeightCm()));
        if (max > MAX_SIDE_CM) return "найбільша сторона " + max + " см перевищує " + MAX_SIDE_CM + " см";
        return null;
    }
}
