package ua.parcelplus.intake;

/** ConcreteHandler: заповненість накладної. */
public class WaybillCompletenessCheck extends ShipmentCheck {
    @Override
    protected String validate(Shipment s) {
        if (s.getSender() == null || s.getSender().isBlank()) return "не вказано відправника";
        if (s.getRecipient() == null || s.getRecipient().isBlank()) return "не вказано одержувача";
        if (s.getItems().isEmpty()) return "не вказано вміст відправлення";
        return null;
    }
}
