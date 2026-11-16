package ua.parcelplus.intake;

/** ConcreteHandler: вимоги до міжнародних відправлень (митна декларація). */
public class InternationalRequirementsCheck extends ShipmentCheck {
    @Override
    protected String validate(Shipment s) {
        if (s.getCustomsDeclaration() == null || s.getCustomsDeclaration().isBlank()) {
            return "для міжнародного відправлення потрібна митна декларація";
        }
        return null;
    }
}
